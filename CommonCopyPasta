JSTL core
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

jQuery CDN (google)
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
  
JSON
  
  
Spring 
  - Autowired용 xml <beans>
	<beans 
	xmlns="http://www.springframework.org/schema/beans"
	xmlns:context="http://www.springframework.org/schema/context"
	
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">
	
	<!-- 어노테이션을 이용한 DI를 허용하는 태그 -->
	<context:annotation-config />
	
  - Logger설정 in Controller (log4J 라이브러리 사용)
  	in member field	
  		private static final Logger logger = LoggerFactory.getLogger(컨트롤러클래스.class);
	
	in src/main/resources/log4j.xml
		<!-- Application Loggers -->
		<logger name="package name">
			<level value="info" />
		</logger>
	
   - OJDBC6 repository 설정, pom.xml 파일 내 설정 필요 
	....</properties>
	
	<!-- OJDBC6를 위한 추가 저장소 설정  -->
	<repositories>
		<repository>
			<id>oracle</id>
			<url>http://maven.jahia.org/maven2</url>
					
		</repository>
	</repositories>
		
	<dependencies>....
		
	in dependencies 
		<!-- OJDBC6 -->
		<dependency>
			<groupId>com.oracle</groupId>
			<artifactId>ojdbc6</artifactId>
			<version>12.1.0.2</version>
		</dependency>
		
   - MyBatis Log4jdbc, 
    	in root-context.xml 
    	<!-- 마이바티스로깅 -->
	<!-- 	-> 마이바티스 dataSource의 프록시(Proxy) -->
	<bean id="dataSource" class="net.sf.log4jdbc.Log4jdbcProxyDataSource">
		<!-- 프록시 대상 DB DataSource설정하기 (원본 DB 정보) -->
		<constructor-arg ref="dataSourceSpied" />
		
		<!-- 로그 출력 양식(포멧) 설정 -->
		<property name="logFormatter">
			<bean class="net.sf.log4jdbc.tools.Log4JdbcCustomFormatter">
				<property name="loggingType" value="MULTI_LINE" />
				<property name="sqlPrefix" value="SQL:::" />
			</bean>
		</property>
	</bean>
	
	in log4j.xml
	<!-- Log4jdbc logger 설정 -->
	<logger name="jdbc.connection"><level value="warn" /></logger>
	<logger name="jdbc.audit"><level value="warn" /></logger>
	<logger name="jdbc.sqlonly"><level value="warn" /></logger>
	<logger name="jdbc.sqltiming"><level value="info" /></logger>
	<logger name="jdbc.resultset"><level value="warn" /></logger>
	<logger name="jdbc.resultsettable"><level value="info" /></logger>
	<logger name="org.mybatis"><level value="warn" /></logger>

   - Spring UTF-8 config
   	<!-- 한글 인코딩 설정 필터 : UTF-8 -->
	<!-- web.xml 파일의 첫 부분에 작성한다 -->
	<filter>
		<filter-name>encodingFilter</filter-name>
		<filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
		<init-param>
			<param-name>encoding</param-name>
			<param-value>UTF-8</param-value>
		</init-param>
	</filter>
	
	<filter-mapping>
		<filter-name>encodingFilter</filter-name>
		<url-pattern>/*</url-pattern>
	</filter-mapping>

    - Interceptor 설정 
    	in servlet-context.xml
	<interceptors>
		<interceptor>
			<mapping path="/intercepter/admin/**"/>
			<beans:bean class="interceptor.interceptor.AdminInterceptor"></beans:bean>
		</interceptor>
	</interceptors>
	
    - 스프링 파일업로드 클래스 
	org.springframework.web.multipart.commons.CommonsMultipartResolver
	
    - 파일이름 랜덤 생성 
	String filename = fileupload.getOriginalFilename(); //원본 파일명
	filename += UUID.randomUUID().toString().split("-")[4]; //UUID 추가
    
    - Spring x JSON
   	TODO

    - Transaction
    	in servlet-contextxml, <beans:bean> add
	  xmlns:tx="http://www.springframework.org/schema/tx"
	in xsi:schemaLocation, add   
	  http://www.springframework.org/schema/tx https://www.springframework.org/schema/tx/spring-tx.xsd
	add
	  <tx:annotation-driven/>
	
	in root-context.xml
	  
    	
	
