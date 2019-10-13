package cn.houlinan.mylife.service;

import cn.houlinan.mylife.DTO.NoteVO;
import cn.houlinan.mylife.constant.NoteConstant;
import cn.houlinan.mylife.context.UserContext;
import cn.houlinan.mylife.entity.Note;
import cn.houlinan.mylife.entity.User;
import cn.houlinan.mylife.entity.primary.repository.NoteRepository;
import cn.houlinan.mylife.service.common.MyPage;
import cn.houlinan.mylife.service.common.PrimaryBaseService;
import cn.houlinan.mylife.utils.CMyString;
import cn.houlinan.mylife.utils.org.n3r.idworker.Sid;
import com.google.common.collect.Maps;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2019/10/1
 * Time : 6:01
 */
@Service
public class NoteService {

    @Autowired
    NoteRepository noteRepository ;

    @Autowired
    Sid sid ;

    @Autowired
    private PrimaryBaseService primaryBaseService;


    public Note save(NoteVO noteVO){

        Note note = new Note() ;
        BeanUtils.copyProperties(noteVO, note);

        User user = UserContext.getUser() ;

        if(CMyString.isEmpty(noteVO.getId()) || noteRepository.findNoteById(noteVO.getId()) == null ){

            note.setUserId(user.getId());
            note.setTeamid(user.getTeamid());
            note.setCrTime(new Date());
            note.setUpdateTime(new Date());
            if(!CMyString.isEmpty(noteVO.getPassword()))
                note.setIsNeedPwd(NoteConstant.IS_NEED_PASSWORD_YES);
        }

        noteRepository.save(note);

        return note ;
    }


    public MyPage<Note> findNotesByUser(User user , int nStartPage, int nPageSize)throws Exception{


        String sql = "FROM NOTE WHERE 1=1 AND USERID = " + user.getId()
                + " AND TEAMID = " + user.getTeamid() ;

        //SELECT *  count(1)

        return primaryBaseService.executeSqlByPage("SELECT * " + sql , "SELECT COUNT(1) " + sql,
                Maps.newHashMap() , nStartPage , nPageSize ,Note.class );
    }




}
