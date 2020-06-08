package cn.houlinan.mylife.controller;

import ch.qos.logback.classic.Logger;
import cn.houlinan.mylife.constant.CommentConstant;
import cn.houlinan.mylife.constant.UserConstant;
import cn.houlinan.mylife.entity.Comment;
import cn.houlinan.mylife.entity.User;
import cn.houlinan.mylife.entity.primary.repository.CommentRepository;
import cn.houlinan.mylife.entity.primary.repository.UserRepository;
import cn.houlinan.mylife.utils.CMyString;
import cn.houlinan.mylife.utils.HHJSONResult;
import cn.houlinan.mylife.utils.RedisOperator;
import cn.houlinan.mylife.utils.WechatMessageSendUtil;
import cn.houlinan.mylife.utils.org.n3r.idworker.Sid;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2020/5/30
 * Time : 13:31
 */
@RestController
@Slf4j
@RequestMapping("/message")
@Api(value = "发送微信信息相关", tags = "发送微信信息相关")
public class MessageController {

    @Autowired
    Sid sid ;

    @Autowired
    UserRepository userRepository ;

    @Autowired
    CommentRepository commentRepository ;

    @Value("${user.errr.number}")
    private int userErrorTimes;

    @Autowired
    RedisOperator redisOperator;

    @RequestMapping("/sendToMe")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "message", value = "正文内容", required = true, dataType = "String", paramType = "query", defaultValue = "你好")
    })
    @ApiOperation(value = "发送信息到自己微信", notes = "发送信息到自己微信")
    public HHJSONResult sendToMe(@RequestParam(name = "message", required = false ) String message,
                                 @RequestParam(name = "openId", required = false ) String openId){

        User userByOpenId = userRepository.findUserByOpenId(openId);
        if(userByOpenId != null ){
            String errorTimeStr = redisOperator.get(UserConstant.RESET_USER_ERROR_TIME + ":" + userByOpenId.getId());
            if(!CMyString.isEmpty(errorTimeStr)){
                int errorTimes = Integer.valueOf(errorTimeStr);
                if(errorTimes > userErrorTimes ) return HHJSONResult.errorMsg("今日您的发送已经超出了限制次数");
            }



        }

        if(CMyString.isEmpty(message)) return HHJSONResult.errorMsg("请输入内容哦！");
//        WechatMessageSendUtil.sendMessageByServerChan( URLEncoder.encode("主任收到了一个小程序的意见反馈"), URLEncoder.encode(message) );
        WechatMessageSendUtil.sendMessageByServerChan( "主任收到了一个小程序的意见反馈",message);

        //执行异步方法
        ThreadUtil.execAsync(() -> {

            log.info("这里准备保存用户的评论内容，用户openId【{}】" ,openId);
            if( userByOpenId != null){
                log.info("用户名为【{}】,准备保存评论,内容为：{}" ,userByOpenId.getUserName() , message);
                //将用户的建议存如评论表中
                Comment comment = Comment.builder()
                        .comment(message)
                        .messageType(CommentConstant.COMMENT_TYPE_SUGGESTIONS)
                        .commentUser(userByOpenId)
                        .parentId("")
                        .productId(CommentConstant.COMMENT_PRODUCT_TYPE_GELUOMI).build();
                comment.setId(sid.nextShort().trim());
                comment.setTeamid(userByOpenId.getTeamid());
                comment.setTeam(userByOpenId.getTeam());
                commentRepository.save(comment);
                log.info("保存评论【{}】成功：请查收" ,comment.getId());

                if(userByOpenId.getUserType() != UserConstant.USER_TYPE_ADMIN)
                    redisOperator.set(UserConstant.RESET_USER_ERROR_TIME + ":" + userByOpenId.getId() ,
                            (Integer.valueOf(redisOperator.get(UserConstant.RESET_USER_ERROR_TIME + ":" + userByOpenId.getId())) +1) + "");
            }
        });

        return HHJSONResult.ok();
    }



    @RequestMapping("/queryMessageByOpenId")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "message", value = "正文内容", required = true, dataType = "String", paramType = "query", defaultValue = "你好")
    })
    @ApiOperation(value = "查询用户的评论", notes = "查询用户的评论")
    public HHJSONResult queryMessageByOpenId(
                                 @RequestParam(name = "openId", required = false ) String openId){


        User user = userRepository.findUserByOpenId(openId);
        if(user == null) return HHJSONResult.errorMsg(StrUtil.format("未检测到【{}】的用户数据信息" , openId));

        List<Comment> commentsByCommentUser = commentRepository.findCommentsByCommentUserAndMessageTypeOrderByCrTimeDesc(user , CommentConstant.COMMENT_TYPE_SUGGESTIONS);
        List<String> commentIds = new ArrayList<>();
        commentsByCommentUser.forEach(a -> commentIds.add(a.getId()));

        List<Comment> commentByIdIn = commentRepository.findCommentByParentIdIn(commentIds);
        Map<String , Comment> commentMap = new HashMap<>();
        commentByIdIn.forEach( a -> commentMap.put(a.getParentId() , a));

        JSONArray result = new JSONArray();
        commentsByCommentUser.forEach(a -> {
            if(!CMyString.isEmpty(a.getParentId())) return  ;
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message" , a.getComment());
            Comment comment = commentMap.get(a.getId());
            if(comment == null ){
                jsonObject.put("toMessage", "未收到回复");
                jsonObject.put("toCommentUserName", "");
            }else{
                jsonObject.put("toMessage", comment.getComment());
                jsonObject.put("toCommentUserName", comment.getCommentUser().getNikeName());
            }
            jsonObject.put("date", DateUtil.formatDateTime(a.getCrTime()));
            result.add(jsonObject);

    });
        return HHJSONResult.ok(result);
    }


    @RequestMapping("/queryAdminMessage")
    @ApiOperation(value = "查询发送给admin超管的信息", notes = "查询发送给admin超管的信息   ")
    public HHJSONResult queryAdminMessage(User user){
        if(user == null ) return HHJSONResult.errorMsg("您未登录");
        if(user.getUserType() != UserConstant.USER_TYPE_ADMIN) return HHJSONResult.errorMsg("您没有权限处理此操作");
        List<Comment> queryResultComment = commentRepository.findCommentsByParentIdAndMessageTypeAndProductIdAndHasProcessed(
                "", CommentConstant.COMMENT_TYPE_SUGGESTIONS, CommentConstant.COMMENT_PRODUCT_TYPE_GELUOMI,
                CommentConstant.COMMENT_HAH_PROCESSED_FALSE);

        JSONArray result = new JSONArray();
        queryResultComment.forEach( a ->{
            JSONObject json = new JSONObject();
            json.put("id" ,a.getId());
            json.put("userName" ,a.getCommentUser().getNikeName() );
            json.put("message" ,a.getComment() );
            json.put("date" , DateUtil.formatDateTime(a.getCrTime()));
            json.put("userHeadPic" ,a.getCommentUser().getHeadPic() );
            result.add(json);
        });
        return HHJSONResult.ok(result);
    }

    @PostMapping("/processedComment")
    @ApiOperation(value = "超管处理用户建议", notes = "超管处理用户建议")
    public HHJSONResult processedComment(User user ,
                                         @RequestParam(name = "data" , required = false ) String data ,
                                         @RequestParam(name = "commentId" , required = false )String commentId ){
        if(user == null ) return HHJSONResult.errorMsg("您未登录");
        if(user.getUserType() != UserConstant.USER_TYPE_ADMIN) return HHJSONResult.errorMsg("您没有权限处理此操作");

        if(CMyString.isEmpty(data)) return HHJSONResult.errorMsg("回复内容为空");

        Comment commentById = commentRepository.findCommentById(commentId);
        if(commentById == null ) return HHJSONResult.errorMsg("信息不存在");
        //将用户的建议存如评论表中
        Comment comment = Comment.builder()
                .comment(data)
                .messageType(CommentConstant.COMMENT_TYPE_SUGGESTIONS)
                .commentUser(user)
                .parentId(commentId)
                .productId(CommentConstant.COMMENT_PRODUCT_TYPE_GELUOMI)
                .hasProcessed(CommentConstant.COMMENT_HAH_PROCESSED_TRUE)
                .build();
        comment.setId(sid.nextShort().trim());
        comment.setTeamid(user.getTeamid());
        comment.setTeam(user.getTeam());
        commentRepository.save(comment);

        commentById.setHasProcessed(CommentConstant.COMMENT_HAH_PROCESSED_TRUE);
        commentRepository.save(commentById);
        log.info("处理评论【{}】。处理的结果是：【{}】成功：请查收" ,commentById.getComment() ,data);
        return HHJSONResult.ok();
    }


    @GetMapping("/deleteComment")
    @ApiOperation(value = "删除评论", notes = "删除评论")
    public HHJSONResult processedComment(User user ,
                                         @RequestParam(name = "commentId" , required = false )String commentId ) {

        if(user == null ) return HHJSONResult.errorMsg("您未登录");
        if(user.getUserType() != UserConstant.USER_TYPE_ADMIN) return HHJSONResult.errorMsg("您没有权限处理此操作");


        Comment commentById = commentRepository.findCommentById(commentId);
        if(commentById != null )
            commentRepository.delete(commentById);

        return HHJSONResult.ok();
    }

}
