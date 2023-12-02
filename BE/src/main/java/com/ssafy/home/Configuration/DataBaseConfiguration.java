package com.ssafy.home.Configuration;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

//히카리 DBCP를 사용하기 위한 밑작업
@Configuration
@PropertySource("classpath:/application.properties")
@MapperScan(basePackages = {"com.ssafy.home.*.model.mapper"})
public class DataBaseConfiguration {
	
	final ApplicationContext applicationContext;

	public DataBaseConfiguration(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	
	@Bean
	@ConfigurationProperties(prefix = "spring.datasource.hikari")
	public HikariConfig hikariConfig() {
		return new HikariConfig();
	}

	@Bean
	public DataSource dataSource() {
		return new HikariDataSource(hikariConfig());
	}

	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
		SqlSessionFactoryBean session = new SqlSessionFactoryBean();
		session.setDataSource(dataSource);
		session.setMapperLocations(applicationContext.getResources("classpath:mapper/**/*.xml"));
		
		//AliasPackage를 써주면, 해당 주소의 클래스의 이름을 앞대가리 소문자로 바꿔서 별칭으로 지정해준다. 
		session.setTypeAliasesPackage("com.ssafy.home.*.model");
		return session.getObject();
	}

	@Bean
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
}
