# SpringBoot项目框架

`SpringBoot项目搭建 ，包含了日期、Http、字符串工具类，前端模板为：thymeleaf、freemarker。数据加解密。spring-boot默认日志 commen-logging。`
## 依赖
    1.spring-boot-starter-parent 2.1.5.RELEASE
    2.spring-boot-starter-web
    3.mysql-connector-java
    4.spring-boot-starter-data-jpa
    5.spring-boot-configuration-processor
    6.spring-boot-devtools
    7.spring-boot-starter-thymeleaf
    8.spring-boot-starter-freemarker
    9.fastjson
    10.junit

## 项目启动
    1.创建数据库spring_boot
    2.修改 application.properties 文件中的数据库配置
    3.启动类： com.dling.springboot.Starter
    4.启动项目后，会根据bean自动生成表
    
## 加解密
`前后端分离时使用，将前端传递的加密数据解密，然后将返回前端的json数据进行加密后传给前端。前端解密后使用。`
    
    1.修改 application.properties 文件中的加解密配置，该配置需要 和 前端的加解密配置 相同。否则解密会失败。

    2.控制器方法上增加注解 @Encrypt、@RequestBody，如下：
    @Encrypt
    @PostMapping("/querymap/{id}")
    String querymap(@PathVariable Integer id, HttpServletRequest request, @RequestBody String body) {
        
        System.out.println("body-->" + body);
        // 此处使用阿里的fastjson，将json串转换为json对象，然后通过该对象获取数据
        JSONObject data = JSON.parseObject(body);
        String name = data.getString("name");
        Integer age = data.getInteger("age");
        
        // 进行逻辑处理....

        // 返回前端数据
        return JSON.toJSONString(msgRepository.queryById(id));
    }
    
## HttpKit工具类

    1.发送get请求
        System.out.println("Get--->"+HttpKit.get("https://api.apiopen.top/getAllUrl"));
        
    2.发送post请求，并传参
        Map<String, String> query = new HashMap<>();
        query.put("page", "1");
        query.put("count", "5");
        System.out.println("Post-->"+HttpKit.post("https://api.apiopen.top/getWangYiNews", query, null));
        
    ...
        
## DateKit工具类

    1.获取毫秒、时间戳
    2.获取指定日期的星期
    3.将指定日期加上某个类型的值后，得到新的日期
    4.计算两个日期间的差值，毫秒、秒、时等等
    ...
    
## StringKit工具类

    1.获取32位随机字符串
    2.获取36位随机字符串
    3.判断一个String是否为空
    ...
