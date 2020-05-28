package cn.houlinan.mylife.listener;

import cn.houlinan.mylife.constant.UserConstant;
import cn.houlinan.mylife.entity.BaseEntity;
import cn.houlinan.mylife.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Component
public class BaseEntityUpdateListener {

    private HttpServletRequest request;
    private HttpSession session;

    @PrePersist
    public void onCreate(BaseEntity baseEntity) {
        String crUser = "";
        Long teamId = null;
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (servletRequestAttributes == null)
            return;
        request = servletRequestAttributes.getRequest();
        if (request != null) {
            session = request.getSession();
//            Object loginUser = session.getAttribute(UserConstant.SESSION_LOGIN_USER);
            User loginUser =(User)session.getAttribute(UserConstant.SESSION_LOGIN_USER);
            if (loginUser != null) {
                crUser = String.valueOf(loginUser.getEmail());
                teamId = loginUser.getTeamid();
            }
        }
        if(baseEntity.getTeamid()==null){
            baseEntity.setTeamid(teamId);
        }
    }

    @PreUpdate
    public void onUpdate(BaseEntity baseEntity) {
        String updateUser = "";
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (servletRequestAttributes == null)
            return;
        request = servletRequestAttributes.getRequest();
        if (request != null) {
            session = request.getSession();
//            Object loginUser = session.getAttribute(UserConstant.SESSION_LOGIN_USER);
            User loginUser =(User) session.getAttribute(UserConstant.SESSION_LOGIN_USER);
            if (loginUser != null) {
                updateUser = String.valueOf(loginUser.getEmail());
            }
        }
    }
}
