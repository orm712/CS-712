FROM openjdk:17-jdk

ARG JAR_FILE=./build/libs/auth-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]

# 자바의 경우 JVM 특성상 빌드 후 jar 파일만 넘겨주면 되지만,

# 파이썬이나 리액트같은 경우 전체 파일을 복사하여 넘겨주고,
# 각 dependency들도 따로 설치해줘야 한다.