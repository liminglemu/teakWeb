# 使用 OpenJDK 17 作为基础镜像
FROM openjdk:17-jdk-slim

# 设置工作目录
WORKDIR /app

# 复制主应用 JAR 到容器
COPY jars/blog_admin-1.0-SNAPSHOT.jar /app/app.jar

# 复制依赖 JAR 到容器的 libs 目录
#COPY ../libs/*.jar /app/libs/

# 暴露 Spring Boot 默认端口
EXPOSE 8001

# 启动命令（指定类路径）
ENTRYPOINT ["java", "-jar", "app.jar"]