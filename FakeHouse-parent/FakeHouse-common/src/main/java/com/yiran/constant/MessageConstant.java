package com.yiran.constant;
/**
 * 消息常量
 */
public class MessageConstant {


    // 验证码
    public static final String TELEPHONE_VALIDATECODE_NOTNULL = "手机号和验证码都不能为空";
    public static final String VALIDATECODE_ERROR = "验证码输入错误";
    public static final String SEND_VALIDATECODE_FAIL = "验证码发送失败";
    public static final String SEND_VALIDATECODE_SUCCESS = "验证码发送成功";


    // 登录
    public static final String LOGIN_SUCCESS = "登录成功";
    public static final String GET_USERNAME_SUCCESS = "获取当前登录用户名称成功";
    public static final String GET_USERNAME_FAIL = "获取当前登录用户名称失败";
    public static final String GET_MENU_SUCCESS = "获取当前登录用户菜单成功";
    public static final String GET_MENU_FAIL = "获取当前登录用户菜单失败";

    // 数据统计 报表制作
    public static final String GET_MEMBER_NUMBER_REPORT_SUCCESS = "获取会员统计数据成功";
    public static final String GET_MEMBER_NUMBER_REPORT_FAIL = "获取会员统计数据失败";
    public static final String GET_SETMEAL_COUNT_REPORT_SUCCESS = "获取套餐统计数据成功";
    public static final String GET_SETMEAL_COUNT_REPORT_FAIL = "获取套餐统计数据失败";
    public static final String GET_BUSINESS_REPORT_SUCCESS = "获取运营统计数据成功";
    public static final String GET_BUSINESS_REPORT_FAIL = "获取运营统计数据失败";

    // 权限管理-权限
    public static final String QUERY_PERMISSION_SUCCESS = "查询权限数据成功";
    public static final String QUERY_PERMISSION_FAIL = "查询权限数据失败";
    public static final String ADD_PERMISSION_SUCCESS = "添加权限成功";
    public static final String ADD_PERMISSION_FAIL = "添加权限失败";
    public static final String DELETE_PERMISSION_FAIL = "删除权限失败";
    public static final String DELETE_PERMISSION_SUCCESS = "删除权限成功";
    public static final String EDIT_PERMISSION_SUCCESS = "修改权限成功";
    public static final String EDIT_PERMISSION_FAIL = "修改权限失败";

    // 权限管理-角色
    public static final String QUERY_ROLE_SUCCESS = "查询角色成功";
    public static final String QUERY_ROLE_FAIL = "查询角色失败";
    public static final String ADD_ROLE_SUCCESS = "添加角色成功";
    public static final String ADD_ROLE_FAIL = "添加角色失败";
    public static final String EDIT_ROLE_SUCCESS = "修改角色成功";
    public static final String EDIT_ROLE_FAIL = "修改角色失败";
    public static final String DELETE_ROLE_SUCCESS = "删除角色成功";
    public static final String DELETE_ROLE_FAIL = "删除角色失败";
    public static final String FIND_PERMISSION_ID_SUCCESS = "查询角色关联的权限id成功";
    public static final String FIND_PERMISSION_ID_FAIL = "查询角色关联的权限id失败";
    public static final String FIND_MENU_ID_SUCCESS = "查询角色关联的菜单id成功";
    public static final String FIND_MENU_ID_FAIL = "查询角色关联的菜单id失败";
    public static final String FIND_LINK_ID_SUCCESS = "查询角色关联的权限和菜单id成功";
    public static final String FIND_LINK_ID_FAIL = "查询角色关联的权限和菜单id失败";

    // 权限管理-user
    public static final String QUERY_USER_SUCCESS = "查询用户成功";
    public static final String QUERY_USER_FAIL = "查询用户失败";
    public static final String ADD_USER_SUCCESS = "添加用户成功";
    public static final String ADD_USER_FAIL = "添加用户失败";
    public static final String USERNAME_ERROR = "用户名重复";
    public static final String UPDATE_USER_SUCCESS = "修改用户信息成功";
    public static final String UPDATE_USER_FAIL = "修改用户信息失败";
    public static final String DELETE_USER_SUCCESS = "删除用户成功";
    public static final String DELETE_USER_FAIL = "删除用户失败";
    public static final String USER_REGISTER_SUCCESS = "用户注册成功";
    public static final String USER_REGISTER_FAIL = "用户注册失败";


    // 菜单管理
    public static final String QUERY_MENU_SUCCESS = "查询菜单信息成功";
    public static final String QUERY_MENU_FAIL = "查询菜单信息失败";
    public static final String ADD_MENU_SUCCESS = "添加菜单成功";
    public static final String ADD_MENU_FAIL = "添加菜单失败";
    public static final String UPDATE_MENU_SUCCESS = "修改菜单信息成功";
    public static final String UPDATE_MENU_FAIL = "修改菜单信息失败";
    public static final String DELETE_MENU_SUCCESS = "删除菜单成功";
    public static final String DELETE_MENU_FAIL = "删除菜单失败";


    // 游戏管理
    public static final String QUERY_GAME_SUCCESS = "查询游戏信息成功";
    public static final String QUERY_GAME_FAIL = "查询游戏信息失败";
    public static final String ADD_GAME_SUCCESS = "添加游戏成功";
    public static final String ADD_GAME_FAIL = "添加游戏失败";
    public static final String UPDATE_GAME_SUCCESS = "更新游戏成功";
    public static final String UPDATE_GAME_FAIL = "更新游戏失败";
    public static final String DELETE_GAME_SUCCESS = "删除游戏成功";
    public static final String DELETE_GAME_FAIL = "删除游戏失败";



    // 分类数据管理
    public static final String QUERY_CATEGORY_BY_GAMEID_SUCCESS = "查询游戏分类信息成功";
    public static final String QUERY_CATEGORY_BY_GAMEID_FAIL = "查询游戏分类信息失败";
    public static final String QUERY_CATEGORY_SUCCESS = "查询分类信息成功";
    public static final String QUERY_CATEGORY_FAIL = "查询分类信息失败";
    public static final String UPDATE_CATEGORY_SUCCESS = "修改分类信息成功";
    public static final String UPDATE_CATEGORY_FAIL = "修改分类信息失败";
    public static final String ADD_CATEGORY_SUCCESS = "添加分类信息成功";
    public static final String ADD_CATEGORY_FAIL = "添加分类信息失败";
    public static final String CATEGORY_NAME_EXISTS = "分类名称重复";

    public static final String DELETE_CATEGORY_SUCCESS = "删除分类信息成功";
    public static final String DELETE_CATEGORY_FAIL = "删除分类信息失败";
    public static final String QUERY_CATEGORY_ID_SUCCESS = "查询分类id失败";
    public static final String QUERY_CATEGORY_ID_FAIL = "查询分类id失败";


    // decoder管理
    public static final String QUERY_DECODER_SUCCESS = "查询decoder成功";
    public static final String QUERY_DECODER_FAIL = "查询decoder失败";
    public static final String UPDATE_DECODER_SUCCESS = "修改decoder成功";
    public static final String UPDATE_DECODER_FAIL = "修改decoder失败";
    public static final String ADD_DECODER_SUCCESS = "添加decoder成功";
    public static final String ADD_DECODER_FAIL = "添加decoder失败";
    public static final String DECODER_EXISTS = "decoder已存在";
    public static final String DELETE_DECODER_SUCCESS = "删除decoder成功";
    public static final String DELETE_DECODER_FAIL = "删除decoder失败";
    public static final String DECODER_CONNECTED = "decoder被关联";


    // 游戏特色数据管理
    public static final String QUERY_CHARACTER_SUCCESS = "查询游戏特色数据成功";
    public static final String QUERY_CHARACTER_FAIL = "查询游戏特色数据失败";
    public static final String UPDATE_CHARACTER_SUCCESS = "修改游戏特色数据成功";
    public static final String UPDATE_CHARACTER_FAIL = "修改游戏特色数据失败";
    public static final String ADD_CHARACTER_SUCCESS = "添加游戏特色数据成功";
    public static final String ADD_CHARACTER_FAIL = "添加游戏特色数据失败";
    public static final String DELETE_CHARACTER_SUCCESS = "删除游戏特色数据成功";
    public static final String DELETE_CHARACTER_FAIL = "删除游戏特色数据失败";
    public static final String DETAILID_NOT_EXIST = "游戏详情id不存在";



    // 游戏详情数据
    public static final String QUERY_DETAILGAME_SUCCESS = "查询游戏详情数据成功";
    public static final String QUERY_DETAILGAME_FAIL = "查询游戏详情数据失败";
    public static final String UPDATE_DETAILGAME_SUCCESS = "修改游戏详情数据成功";
    public static final String UPDATE_DETAILGAME_FAIL = "修改游戏详情数据失败";
    public static final String ADD_DETAILGAME_SUCCESS = "添加游戏详情数据成功";
    public static final String ADD_DETAILGAME_FAIL = "添加游戏详情数据失败";
    public static final String DELETE_DETAILGAME_SUCCESS = "删除游戏详情数据成功";
    public static final String DELETE_DETAILGAME_FAIL = "删除游戏详情数据失败";
    public static final String DETAILGAME_CONNECTED = "游戏详情被关联";

}