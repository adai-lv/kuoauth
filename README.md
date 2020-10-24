# KuOAuth

### 介绍
集成第三方授权登录的工具箱，它可以让开发者脱离繁琐的第三方登录SDK，让实现登录功能变得方便!

### 术语

- `clientId` 客户端身份标识符（应用id），在申请完 `Oauth应用` 后，由第三方平台颁发，具有唯一性
- `clientSecret` 客户端密钥，在申请完 `Oauth应用` 后，由第三方平台颁发
- `redirectUri` 开发者项目中的有效api地址。用户在确认第三方平台授权（登录）后，第三方平台会重定向到该地址，并携带code等参数
- `state` 用来保持授权会话流程完整性，防止CSRF攻击的安全的随机的参数，由开发者生成
- `alipayPublicKey` 支付宝公钥。当选择支付宝登录时，必传该值，由开发者生成
- `unionId` 是否需要申请unionid，目前只针对qq登录。注：qq授权登录时，获取unionid需要单独发送邮件申请权限。如果个人开发者账号中申请了该权限，可以将该值置为true，在获取openId时就会同步获取unionId。参考链接：[UnionID介绍](http://wiki.connect.qq.com/unionid%E4%BB%8B%E7%BB%8D)
- `stackOverflowKey` Stack Overflow 登陆时需单独提供的key，由第三方平台颁发
- `agentId` 企业微信登陆时需单独提供该值，由第三方平台颁发，为授权方的网页应用ID
- `platform` KuOAuth 支持的第三方平台，比如：GITHUB、GITEE等
- `openId` 为第三方平台的用户ID。以下几个平台需特别注意：
  - 钉钉、抖音：openId 为用户的 unionid
  - 微信开放平台登录、QQ：平台也支持获取 unionId
  - Google：openId 为用户的 sub，sub为Google的所有账户体系中用户唯一的身份标识符，详见：[OpenID Connect](https://developers.google.com/identity/protocols/oauth2/openid-connect)

注：建议通过 `kuuid + platform` 的方式唯一确定一个用户，这样可以解决用户身份归属的问题。因为 `单个用户ID` 在某一平台中是唯一的，但不能保证在所有平台中都是唯一的。

### 快速开始

#### 准备工作
1. 申请注册第三方平台的开发者账号
2. 创建第三方平台的应用，获取配置信息(accessKey, secretKey, redirectUri)

#### 使用方式
- 引入依赖
```
<dependency>
  <groupId>com.kupug.kuoauth</groupId>
  <artifactId>kupug-kuoauth</artifactId>
  <version>${latest.version}</version>
</dependency>
```

- 调用服务 API
```
// 构建 OAuth 平台配置
KuOAuthConfig config = KuOAuthConfig.builder()
    .clientId("clientId")
    .clientSecret("clientSecret")
    .redirectUri("redirectUri")
    .build();

// 创建 OAuth 平台对象
KuOAuthPlatform platform = PlatformFactory.newInstance(Platform.QQ, kuOAuthConfig);

// 构建 OAuth 授权页面 URL
String authorizeUrl = platform.authorize("state");

// 授权登录后会回调 redirectUri，并带上 code、state
// 构建回调参数对象
KuOAuthCallback oAuthCallback = KuOAuthCallback.buider()
    .code("authorize code")
    .state("authorize state")
    .build();

// 授权登录
KuOAuthLogin oAuthLogin = platform.login(oAuthCallback);
```

### 版本迭代
#### v1.1 2020-10-24
- Google、Facebook、阿里云平台授权登录功能逻辑开发

#### v1.0 2020-10-23
- feat: 工具助手uuid、url、collection、string等基本功能逻辑开发
- feat: 基本 okhttp 网络组件，构建 HttpClient 类工具
- feat: 基本 jackson 组件，构建 json 转换和解析的工具助手（有待完善）
- feat: OAuth config、token、user、login 等通用逻辑抽象
- feat: Gitee、Github、QQ、Wechat、Weibo、Dingtalk 平台授权登录功能逻辑开发