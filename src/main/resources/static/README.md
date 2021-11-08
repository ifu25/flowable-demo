## 关于 modeler 目录说明

Flowable Modeler 在线设计器（`6.5`）

- 提取自官方包：
- `flowable-6.5.0.zip` - `flowable-modeler.war` - `WEB-INF\classes\static`
- 修改过以下文件：
- `scripts/app-cfg.js` 将 `contextRoot` 改为`""`
- `scripts/configuration/url-config.js` 中 `getAccountUrl` 改为自己新增加的 `Controlelr`