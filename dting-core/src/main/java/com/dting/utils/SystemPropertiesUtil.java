package com.dting.utils;

import com.dting.model.SystemPropertiesAbstract;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.Swap;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Properties;

/**
 * *************************************************<br/>
 * 操作系统以及JVM的概要信息<br/>
 * ************************************************<br/>
 *
 * @author huangfu
 * @date 2022/10/16 9:50
 */
public class SystemPropertiesUtil {

    /**
     * 获取java和操作系统的基本概要
     *
     * @throws UnknownHostException 没有主机名的异常
     */
    public static SystemPropertiesAbstract getEnvAbstract() throws UnknownHostException {
        SystemPropertiesAbstract systemPropertiesAbstract = new SystemPropertiesAbstract();
        Runtime r = Runtime.getRuntime();
        Properties props = System.getProperties();
        InetAddress addr = InetAddress.getLocalHost();
        String ip = addr.getHostAddress();
        Map<String, String> map = System.getenv();
        // 获取用户名
        String userName = map.get("USERNAME");
        // 获取计算机名
        String computerName = map.get("COMPUTERNAME");
        // 获取计算机域名
        String userDomain = map.get("USERDOMAIN");

        //用户名
        systemPropertiesAbstract.setUserName(userName);
        //用户的主目录
        systemPropertiesAbstract.setUserHomePath(props.getProperty("user.home"));
        //用户的当前工作目录
        systemPropertiesAbstract.setUserWorkPath(props.getProperty("user.dir"));
        //计算机名
        systemPropertiesAbstract.setComputerName(computerName);
        //计算机域名
        systemPropertiesAbstract.setComputerDomainName(userDomain);
        //本地ip地址
        systemPropertiesAbstract.setLocalIpAddress(ip);
        //本地主机名
        systemPropertiesAbstract.setLocalHostName(addr.getHostName());
        //JVM可以使用的总内存
        systemPropertiesAbstract.setJvmTotalMemory(r.totalMemory());
        //JVM可以使用的剩余内存
        systemPropertiesAbstract.setJvmRemainingMemory(r.freeMemory());
        //JVM可以使用的处理器个数
        systemPropertiesAbstract.setJvmCoreCount(r.availableProcessors());
        //Java的运行环境版本
        systemPropertiesAbstract.setJvmVersion(props.getProperty("java.version"));
        //Java的运行环境供应商
        systemPropertiesAbstract.setJavaEnvSupplier(props.getProperty("java.vendor"));
        //Java供应商的URL
        systemPropertiesAbstract.setJavaSupplierUrl(props.getProperty("java.vendor.url"));
        //Java的安装路径
        systemPropertiesAbstract.setJavaInstallPath(props.getProperty("java.home"));
        //Java的类格式版本号
        systemPropertiesAbstract.setJavaClassFormatVersionNumber(props.getProperty("java.class.version"));
        //默认的临时文件路径
        systemPropertiesAbstract.setDefaultTmpFilePath(props.getProperty("java.io.tmpdir"));
        //操作系统的名称
        systemPropertiesAbstract.setSystemName(props.getProperty("os.name"));
        //操作系统的构架
        systemPropertiesAbstract.setSystemFramework(props.getProperty("os.arch"));
        //操作系统的版本
        systemPropertiesAbstract.setSystemVersion(props.getProperty("os.version"));
        return systemPropertiesAbstract;
    }


    public static void getSystemMemory() throws SigarException {
        Sigar sigar = new Sigar();
        Mem mem = sigar.getMem();
        // 内存总量
        System.out.println("内存总量:    " + mem.getTotal() / 1024L + "K av");
        // 当前内存使用量
        System.out.println("当前内存使用量:    " + mem.getUsed() / 1024L + "K used");
        // 当前内存剩余量
        System.out.println("当前内存剩余量:    " + mem.getFree() / 1024L + "K free");
        Swap swap = sigar.getSwap();
        // 交换区总量
        System.out.println("交换区总量:    " + swap.getTotal() / 1024L + "K av");
        // 当前交换区使用量
        System.out.println("当前交换区使用量:    " + swap.getUsed() / 1024L + "K used");
        // 当前交换区剩余量
        System.out.println("当前交换区剩余量:    " + swap.getFree() / 1024L + "K free");
    }

    public static void main(String[] args) throws SigarException {
        getSystemMemory();
    }


}
