package com.dting.utils;

import com.dting.common.datas.*;
import com.dting.common.datas.NetInfo;
import org.hyperic.sigar.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
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

    /**
     * 获取机器物理内存以及交换区的用量
     *
     * @throws SigarException 异常信息
     */
    public static SystemMemory getSystemMemory() throws SigarException {
        SystemMemory systemMemory = new SystemMemory();
        Sigar sigar = new Sigar();
        //内存获取
        Mem mem = sigar.getMem();
        //交换区获取
        Swap swap = sigar.getSwap();
        // 内存总量
        systemMemory.setTotalMemory(mem.getTotal());
        // 当前内存使用量
        systemMemory.setUseMemory(mem.getUsed());
        // 当前内存剩余量
        systemMemory.setRemainingMemory(mem.getFree());

        // 交换区总量
        systemMemory.setTotalSwap(swap.getTotal());
        // 当前交换区使用量
        systemMemory.setUseSwap(swap.getUsed());
        // 当前交换区剩余量
        systemMemory.setRemainingSwap(swap.getFree());
        return systemMemory;
    }

    /**
     * 操作系统的cpu状态信息
     *
     * @return 操作系统的cpu状态信息
     * @throws SigarException 异常信息
     */
    public static SystemCpuGroup systemCpu() throws SigarException {

        Sigar sigar = new Sigar();
        CpuInfo[] infos = sigar.getCpuInfoList();
        CpuPerc[] cpuList;
        //CPU使用总量  没有格式化成百分比的信息
        //Cpu cpu = sigar.getCpu()
        //cpu 总百分比情况
        CpuPerc cpuPerc = sigar.getCpuPerc();
        //获取CPU的总消耗
        SystemCpuGroup cpuGroup = new SystemCpuGroup();
        //设置CPU组的基本状态
        setCpuInfo(cpuPerc, cpuGroup);
        //多核CPU下获取每一个核的使用状态
        cpuList = sigar.getCpuPercList();
        List<SystemCpu> systemCpus = new ArrayList<>();

        // 不管是单块CPU还是多CPU都适用
        for (int i = 0; i < infos.length; i++) {
            SystemCpu systemCpu = new SystemCpu();

            CpuInfo info = infos[i];
            systemCpu.setCpuSort(i);
            // CPU的总量MHz
            systemCpu.setCpuMhz(info.getMhz());
            // 获得CPU的卖主，如：Intel
            systemCpu.setCpuSuper(info.getVendor());
            // 获得CPU的类别，如：Celeron
            systemCpu.setCpuType(info.getModel());
            // 缓冲存储器数量
            systemCpu.setCpuCacheCount(info.getCacheSize());
            setCpuInfo(cpuList[i], systemCpu);
            systemCpus.add(systemCpu);
        }
        //将每一个核的使用情况放到组里面
        cpuGroup.setSystemCpus(systemCpus);
        return cpuGroup;
    }

    /**
     * 总百分比情况
     *
     * @param cpuPerc       全核CPU的状态信息
     * @param systemCpuInfo 系统CPU配置信息
     */
    private static void setCpuInfo(CpuPerc cpuPerc, SystemCpuInfo systemCpuInfo) {
        //总使用率
        double combined = cpuNumberFormat(cpuPerc.getCombined());
        systemCpuInfo.setCpuTotal(combined);
        //用户使用率
        double user = cpuNumberFormat(cpuPerc.getUser());
        systemCpuInfo.setCpuUserUse(user);
        //系统使用率
        double system = cpuNumberFormat(cpuPerc.getSys());
        systemCpuInfo.setCpuSystemUse(system);
        //错误率
        double error = cpuNumberFormat(cpuPerc.getNice());
        systemCpuInfo.setCpuError(error);
        //等待率
        double wait = cpuNumberFormat(cpuPerc.getWait());
        systemCpuInfo.setCpuWait(wait);
        //空闲率
        double idle = cpuNumberFormat(cpuPerc.getIdle());
        systemCpuInfo.setCpuIdle(idle);

    }

    /**
     * 这里是对CPU的状态信息进行格式化，这里没有使用框架自带的 {@link CpuPerc#format(double)} 是因为框架装换后会带上 '%'
     * 我们为了后期方便展示更期望 他是一个数值类型的数据
     *
     * @param number 要变换的数字
     * @return 使用率  百分制
     */
    private static double cpuNumberFormat(double number) {
        String p = String.valueOf(number * 100.0D);
        int ix = p.indexOf(".") + 1;
        String percent = p.substring(0, ix) + p.charAt(ix);
        return Double.parseDouble(percent);
    }

    /**
     * 网络数据读写信息
     *
     * @return 网络数据读写信息
     * @throws Exception 异常
     */
    public static List<NetInfo> systemNetwork() throws Exception {
        List<NetInfo> netInfos = new ArrayList<>(8);
        Sigar sigar = new Sigar();
        String[] interfaceNames = sigar.getNetInterfaceList();
        for (String interfaceName : interfaceNames) {
            NetInfo netInfo = new NetInfo();
            NetInterfaceConfig interfaceConfig = sigar.getNetInterfaceConfig(interfaceName);
            // 网络设备名
            netInfo.setNetworkName(interfaceName);
            // IP地址
            netInfo.setIpAddress(interfaceConfig.getAddress());
            // 子网掩码
            netInfo.setSubnetMask(interfaceConfig.getNetmask());
            if ((interfaceConfig.getFlags() & 1L) <= 0L) {
                System.out.println("!IFF_UP...skipping getNetInterfaceStat");
                continue;
            }
            NetInterfaceStat ifstat = sigar.getNetInterfaceStat(interfaceName);
            // 接收的总包裹数
            netInfo.setReceivingDataPacketCount(ifstat.getRxPackets());
            // 发送的总包裹数
            netInfo.setSendDataPacketCount(ifstat.getTxPackets());
            // 接收到的总字节数
            netInfo.setReceivingDataByte(ifstat.getRxBytes());
            // 发送的总字节数
            netInfo.setSendDataByte(ifstat.getTxBytes());
            // 接收到的错误包数
            netInfo.setReceivingErrorDataPacketCount(ifstat.getRxErrors());
            // 发送数据包时的错误数
            netInfo.setSendErrorDataPacketCount(ifstat.getTxErrors());
            // 接收时丢弃的包数
            netInfo.setReceivingDiscardedDataPacketCount(ifstat.getRxDropped());
            // 发送时丢弃的包数
            netInfo.setSendDiscardedDataPacketCount(ifstat.getTxDropped());
            netInfos.add(netInfo);
        }
        return netInfos;
    }
}
