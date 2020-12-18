# oauth2
spring boot security oauth2 jwt整合,搭建一个SSO单点登录系统,认证服务和资源服务分离......


#authentication
认证服务: 对身份的认证和授权
除oauth2默认的4中登录模式外,添加支持自定义模式登录
目前项目支持的自定义登录模式为: 微信授权登录,短信验证码登录,账号密码登录
#resource
资源服务: 对身份进行鉴权,并访问权限下的资源
