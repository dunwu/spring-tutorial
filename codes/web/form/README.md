# spring-boot-web-form

> Spring Boot Web 篇表单提交、表单校验示例

## 示例说明

（1）执行 `example.spring.web.form.WebFormApplication#main`，启动 Spring Boot 服务

（2）访问 http://localhost:8080/greeting，可以看到一个简单的表单提交页面。

![](https://raw.githubusercontent.com/dunwu/images/dev/snap/20221010120631.png)

（3）访问 http://localhost:8080/，可以看到另一个简单的表单提交页面，该页面支持对输入项进行校验。

![](https://raw.githubusercontent.com/dunwu/images/dev/snap/20221010120831.png)

（4）也可以直接执行以下测试集，观察测试结果

- `example.spring.web.form.SubmitFormTests`
- `example.spring.web.form.ValidateFormTests`

## 参考资料

- https://spring.io/guides/gs/handling-form-submission/
- https://spring.io/guides/gs/validating-form-input/
- https://beanvalidation.org/
- https://hibernate.org/validator/
