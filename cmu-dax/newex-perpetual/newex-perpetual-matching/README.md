# 1. 单元测试

## 1.1 环境准备

### 1.1.1 数据库

尝试过使用 H2 内存库，/docs/db/perpetual/V1__init.sql 中的脚本已经自动处理到可以被 H2 兼容，但系统中有很多动态创建表的语句，在不入侵生产代码的情况下无法做到适配 H2 的调整，最终决定放弃 H2。

为了没有互相干扰，推荐使用本地搭建数据库，避免在单元测试中与他人发生冲突。

单机 MySQL 启动方法

```bash
docker run -d --name mysql -e MYSQL_ROOT_PASSWORD=root -p 3306:3306  mysql:5.6.40
docker exec -it mysql bash
  #  mysql -u root -proot
  # > create database perpetual;
  # > grant all privileges on perpetual.* to perpetual@`%` identified by '123';
  # > Ctrl + D
  # Ctrl + D
docker exec -i mysql mysql -u perpetual -p123 perpetual < docs/db/perpetual/V1__init.sql
```

### 1.1.2 REDIS

单机启动 Redis 方法

```
docker run -d --name redis -p 6379:6379 redis:3.2.10
```

