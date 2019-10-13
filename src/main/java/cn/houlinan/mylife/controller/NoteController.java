package cn.houlinan.mylife.controller;

import cn.houlinan.mylife.DTO.NoteVO;
import cn.houlinan.mylife.constant.NoteConstant;
import cn.houlinan.mylife.entity.Note;
import cn.houlinan.mylife.entity.User;
import cn.houlinan.mylife.entity.primary.repository.NoteRepository;
import cn.houlinan.mylife.service.NoteService;
import cn.houlinan.mylife.service.common.MyPage;
import cn.houlinan.mylife.utils.BeanValidator;
import cn.houlinan.mylife.utils.CMyString;
import cn.houlinan.mylife.utils.HHJSONResult;
import cn.houlinan.mylife.utils.VerifyUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.naming.Name;
import java.util.List;

/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2019/10/1
 * Time : 11:16
 */
@Controller
@RequestMapping("/note")
@Slf4j
@Api(value = "笔记相关", tags = "笔记相关接口")
public class NoteController {

    @Autowired
    NoteService noteService ;

    @Autowired
    NoteRepository noteRepository ;

    @RequestMapping("/getNotes")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startPage", value = "开始页码", required = false, dataType = "int", paramType="query", defaultValue = "0"),
            @ApiImplicitParam(name = "pageSize", value = "第几页", required = false, dataType = "int", paramType="query", defaultValue = "20")
    })
    @ApiOperation(value = "通过用户查找用户的笔记，支持分页", notes = "通过用户查找用户的笔记，支持分页接口")
    public HHJSONResult findNotesByUser(User user ,
                                        @RequestParam(name = "startPage" ,required = false , defaultValue = "0") int startPage ,
                                        @RequestParam(name = "pageSize" ,required = false , defaultValue =  "20") int pageSize) throws Exception{

        MyPage<Note> notesByUser = noteService.findNotesByUser(user, startPage, pageSize);
        List<Note> content = notesByUser.getContent();

        //将返回值的密码设置为空
        content.stream().forEach( e -> e.setPassword(""));
        notesByUser.setContent(content);


        return HHJSONResult.ok(content);
    }

    @RequestMapping("/save")
    @ResponseBody
    @ApiOperation(value = "保存一个笔记"  , notes = "保存一个笔记，支持update")
    public HHJSONResult save(NoteVO noteVO){

        BeanValidator.check(noteVO);

        Note save = noteService.save(noteVO);
        save.setPassword("");

        return HHJSONResult.ok(save) ;

    }

    @RequestMapping("/findById")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "记录id", required = false, dataType = "String", paramType="query", defaultValue = "0"),
            @ApiImplicitParam(name = "password", value = "密码", required = false, dataType = "String", paramType="query", defaultValue = "20")
    })
    @ApiOperation(value = "根据id查找一个笔记" , notes = "根据id查找一个笔记，有必要时需要输入密码")
    public HHJSONResult getDesc(User user ,
            @RequestParam(name = "id" , required = false) String id ,
            @RequestParam(name = "password" , required = false) String password){

        if(CMyString.isEmpty(id))
            return HHJSONResult.errorMsg("传入的id为空");
        Note note = noteRepository.findNoteById(id) ;

        if(note == null )
            return  HHJSONResult.errorMsg("没有找到相应记录") ;

        if(!user.getId().equals(note.getUserId()))
            return HHJSONResult.errorMsg("该笔记并不属于你");

        if(note.getIsNeedPwd() == NoteConstant.IS_NEED_PASSWORD_NO)

            return HHJSONResult.ok(note);

        if(CMyString.isEmpty(password) || !password.equals(note.getPassword()))
            return HHJSONResult.errorMsg("密码错误");

            return HHJSONResult.ok(note);

    }




}
