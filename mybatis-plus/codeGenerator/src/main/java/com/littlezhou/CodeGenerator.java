package com.littlezhou;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;

public class CodeGenerator {

    public static void main(String[] args) {

        AutoGenerator generator = new AutoGenerator();

        //配置DataSource
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUrl("jdbc:mysql://localhost:3306/ssm_db?serverTimezone=UTC");
        dataSourceConfig.setUsername("root");
        dataSourceConfig.setPassword("password");
        dataSourceConfig.setDriverName("com.mysql.cj.jdbc.Driver");

        generator.setDataSource(dataSourceConfig);

        //配置全局
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setAuthor("小周");
        globalConfig.setOutputDir("D:\\javaeeProject\\mybatis-plus\\genetatedCode\\ssm_generated_demo\\src\\main\\java");
        globalConfig.setOpen(false);
        globalConfig.setMapperName("%sDao");
        globalConfig.setFileOverride(true);
        globalConfig.setIdType(IdType.ASSIGN_ID);

        generator.setGlobalConfig(globalConfig);


        //配置包信息
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent("com.littlezhou");
        packageConfig.setMapper("dao");
        packageConfig.setEntity("domain");
        packageConfig.setService("service");
        packageConfig.setServiceImpl("impl");

        generator.setPackageInfo(packageConfig);


        //策略配置
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setTablePrefix("tbl_");
        strategyConfig.setRestControllerStyle(true);
        strategyConfig.setVersionFieldName("version");//设置乐观锁字段名
        strategyConfig.setLogicDeleteFieldName("deleted");//设置逻辑删除字段名

        generator.setStrategy(strategyConfig);


        generator.execute();
    }
}
