package com.pxtin.android.json;

/**
 * Created by Zneia on 2017/4/23.
 *
 * {"success":true,"data":{"keyWords":"搜索关键词","total":40,"nextPageExist":true,"coll":[{"id":"51","pic_id":"51","tin_id":"0","desc":"描述","tag":"标签","user_id":"1","created_at":"1491575754","link":"/px/51","shortDesc":"简短描述","picInfo":{"fileName":"001.jpg","width":2550,"height":3509,"mime":"image/jpeg","thumb":{"url":"/uploads/pic/001.jpg"}},"userInfo":{"id":"1","name":"AirLind","link":"?userid=1","avatar":"/img/avatar_default.png"},"tinInfo":{"name":null,"link":"/api/search?tinid=0"}}]}}
 */

public class ApiSearch extends ApiColl
{
    private String keyWords;
    private int total;
}
