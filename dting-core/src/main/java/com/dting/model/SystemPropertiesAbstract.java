package com.dting.model;

import java.io.Serializable;

/**
 * *************************************************<br/>
 * 操作系统摘要信息<br/>
 * ************************************************<br/>
 *
 * @author huangfu
 * @date 2022/10/16 9:56
 */
public class SystemPropertiesAbstract implements Serializable {
    private static final long serialVersionUID = 7514460393275434863L;
    /**
     * 用户名
     */
    private String userName;


    /**
     * 用户的主目录
     */
    private String userHomePath;

    /**
     * 用户的当前工作目录
     */
    private String userWorkPath;

    /**
     * 计算机名
     */
    private String computerName;

    /**
     * 计算机域名
     */
    private String computerDomainName;

    /**
     * 本地ip地址
     */
    private String localIpAddress;

    /**
     * 本地主机名
     */
    private String localHostName;

    /**
     * JVM可以使用的总内存
     */
    private Long jvmTotalMemory;

    /**
     * JVM可以使用的剩余内存
     */
    private Long jvmRemainingMemory;

    /**
     * JVM可以使用的处理器个数
     */
    private Integer jvmCoreCount;

    /**
     * Java的运行环境版本
     */
    private String jvmVersion;

    /**
     * Java的运行环境供应商
     */
    private String javaEnvSupplier;

    /**
     * Java供应商的URL
     */
    private String javaSupplierUrl;

    /**
     * Java的安装路径
     */
    private String javaInstallPath;

    /**
     * Java的类格式版本号
     */
    private String javaClassFormatVersionNumber;

    /**
     * 默认的临时文件路径
     */
    private String defaultTmpFilePath;

    /**
     * 操作系统的名称
     */
    private String systemName;

    /**
     * 操作系统的构架
     */
    private String systemFramework;

    /**
     * 操作系统的版本
     */
    private String systemVersion;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserHomePath() {
        return userHomePath;
    }

    public void setUserHomePath(String userHomePath) {
        this.userHomePath = userHomePath;
    }

    public String getUserWorkPath() {
        return userWorkPath;
    }

    public void setUserWorkPath(String userWorkPath) {
        this.userWorkPath = userWorkPath;
    }

    public String getComputerName() {
        return computerName;
    }

    public void setComputerName(String computerName) {
        this.computerName = computerName;
    }

    public String getComputerDomainName() {
        return computerDomainName;
    }

    public void setComputerDomainName(String computerDomainName) {
        this.computerDomainName = computerDomainName;
    }

    public String getLocalIpAddress() {
        return localIpAddress;
    }

    public void setLocalIpAddress(String localIpAddress) {
        this.localIpAddress = localIpAddress;
    }

    public String getLocalHostName() {
        return localHostName;
    }

    public void setLocalHostName(String localHostName) {
        this.localHostName = localHostName;
    }

    public Long getJvmTotalMemory() {
        return jvmTotalMemory;
    }

    public void setJvmTotalMemory(Long jvmTotalMemory) {
        this.jvmTotalMemory = jvmTotalMemory;
    }

    public Long getJvmRemainingMemory() {
        return jvmRemainingMemory;
    }

    public void setJvmRemainingMemory(Long jvmRemainingMemory) {
        this.jvmRemainingMemory = jvmRemainingMemory;
    }

    public Integer getJvmCoreCount() {
        return jvmCoreCount;
    }

    public void setJvmCoreCount(Integer jvmCoreCount) {
        this.jvmCoreCount = jvmCoreCount;
    }

    public String getJvmVersion() {
        return jvmVersion;
    }

    public void setJvmVersion(String jvmVersion) {
        this.jvmVersion = jvmVersion;
    }

    public String getJavaEnvSupplier() {
        return javaEnvSupplier;
    }

    public void setJavaEnvSupplier(String javaEnvSupplier) {
        this.javaEnvSupplier = javaEnvSupplier;
    }

    public String getJavaSupplierUrl() {
        return javaSupplierUrl;
    }

    public void setJavaSupplierUrl(String javaSupplierUrl) {
        this.javaSupplierUrl = javaSupplierUrl;
    }

    public String getJavaInstallPath() {
        return javaInstallPath;
    }

    public void setJavaInstallPath(String javaInstallPath) {
        this.javaInstallPath = javaInstallPath;
    }

    public String getJavaClassFormatVersionNumber() {
        return javaClassFormatVersionNumber;
    }

    public void setJavaClassFormatVersionNumber(String javaClassFormatVersionNumber) {
        this.javaClassFormatVersionNumber = javaClassFormatVersionNumber;
    }

    public String getDefaultTmpFilePath() {
        return defaultTmpFilePath;
    }

    public void setDefaultTmpFilePath(String defaultTmpFilePath) {
        this.defaultTmpFilePath = defaultTmpFilePath;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getSystemFramework() {
        return systemFramework;
    }

    public void setSystemFramework(String systemFramework) {
        this.systemFramework = systemFramework;
    }

    public String getSystemVersion() {
        return systemVersion;
    }

    public void setSystemVersion(String systemVersion) {
        this.systemVersion = systemVersion;
    }
}
