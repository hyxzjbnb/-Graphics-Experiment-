开发一个基于Spring Boot和Spring MVC的自动化仓库管理系统

### 基本需求
1. **定义和文档化一套Restful API**：
   - 需要使用Json或Open API文档格式来定义API。
   - 这要求对API的功能、参数、返回类型等进行详细说明。

2. **在Java中使用Spring MVC和Boot实现API**：
   - 利用Spring Boot框架快速搭建和配置项目基础。
   - 使用Spring MVC来处理HTTP请求和响应。

3. **实现数据仓储层至少使用关系型数据库管理系统(RDBMS)**：
   - 需要设计数据库模型并实现数据交互。
   - 可以选择MySQL、PostgreSQL等流行的RDBMS。

4. **使用Boot测试框架和Web API进行测试**：
   - 编写单元测试和集成测试来验证API的功能和性能。
   - 测试API的响应和处理逻辑是否符合需求。

5. **API认证及必要的Web UI开发**：
   - 实现API的安全认证机制，如OAuth或JWT。
   - 如有需要，还应开发辅助的Web界面。

### 额外实现（加分项）
1. **缓存（Caching）**：
   - 减少数据库访问次数，提高应用性能。
   
2. **会话控制（Session Control）**：
   - 管理用户会话，保证系统的安全性和用户状态的连续性。
   
3. **日志（Log）**：
   - 记录应用运行时的重要信息，便于问题追踪和系统监控。
   
4. **限流（Rate Limiting）**：
   - 控制API的请求频率，防止过载。

### 交付要求
1. **提交源代码和测试截图至代码仓库**（如Github或Gitee）。
2. **提供一份报告**，详细说明开发过程、使用技术、测试结果等。
3. **截止日期**：2023年4月15日。



# Swagger UI
`localhost:8080/doc.html`

# OpenApi
`localhost:8080/v3/api-docs`