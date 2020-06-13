package cn.houlinan.mylife.DTO;

import lombok.Data;

@Data
public class WechatDocument {

    String title ;
    String author ;
    String digest;
    String content ;
    String content_source_url ;
    String thumb_media_id ;
    String show_cover_pic ;
    String url ;
    String thumb_url ;
    int need_open_comment ;
    int only_fans_can_comment ;

}
