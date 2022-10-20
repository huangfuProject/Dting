package com.dting.utils;

import com.dting.common.datas.*;
import com.dting.common.datas.NetDataCollect;
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
    public static SystemPropertiesAbstractCollect getEnvAbstract() throws UnknownHostException {
        SystemPropertiesAbstractCollect systemPropertiesAbstractCollect = new SystemPropertiesAbstractCollect();
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
        systemPropertiesAbstractCollect.setUserName(userName);
        //用户的主目录
        systemPropertiesAbstractCollect.setUserHomePath(props.getProperty("user.home"));
        //用户的当前工作目录
        systemPropertiesAbstractCollect.setUserWorkPath(props.getProperty("user.dir"));
        //计算机名
        systemPropertiesAbstractCollect.setComputerName(computerName);
        //计算机域名
        systemPropertiesAbstractCollect.setComputerDomainName(userDomain);
        //本地ip地址
        systemPropertiesAbstractCollect.setLocalIpAddress(ip);
        //本地主机名
        systemPropertiesAbstractCollect.setLocalHostName(addr.getHostName());
        //JVM可以使用的总内存
        systemPropertiesAbstractCollect.setJvmTotalMemory(r.totalMemory());
        //JVM可以使用的剩余内存
        systemPropertiesAbstractCollect.setJvmRemainingMemory(r.freeMemory());
        //JVM可以使用的处理器个数
        systemPropertiesAbstractCollect.setJvmCoreCount(r.availableProcessors());
        //Java的运行环境版本
        systemPropertiesAbstractCollect.setJvmVersion(props.getProperty("java.version"));
        //Java的运行环境供应商
        systemPropertiesAbstractCollect.setJavaEnvSupplier(props.getProperty("java.vendor"));
        //Java供应商的URL
        systemPropertiesAbstractCollect.setJavaSupplierUrl(props.getProperty("java.vendor.url"));
        //Java的安装路径
        systemPropertiesAbstractCollect.setJavaInstallPath(props.getProperty("java.home"));
        //Java的类格式版本号
        systemPropertiesAbstractCollect.setJavaClassFormatVersionNumber(props.getProperty("java.class.version"));
        //默认的临时文件路径
        systemPropertiesAbstractCollect.setDefaultTmpFilePath(props.getProperty("java.io.tmpdir"));
        //操作系统的名称
        systemPropertiesAbstractCollect.setSystemName(props.getProperty("os.name"));
        //操作系统的构架
        systemPropertiesAbstractCollect.setSystemFramework(props.getProperty("os.arch"));
        //操作系统的版本
        systemPropertiesAbstractCollect.setSystemVersion(props.getProperty("os.version"));
        return systemPropertiesAbstractCollect;
    }

    /**
     * 获取机器物理内存以及交换区的用量
     *
     * @throws SigarException 异常信息
     */
    public static SystemMemoryCollect getSystemMemory() throws SigarException {
        SystemMemoryCollect systemMemoryCollect = new SystemMemoryCollect();
        Sigar sigar = new Sigar();
        //内存获取
        Mem mem = sigar.getMem();
        //交换区获取
        Swap swap = sigar.getSwap();
        // 内存总量
        systemMemoryCollect.setTotalMemory(mem.getTotal());
        // 当前内存使用量
        systemMemoryCollect.setUseMemory(mem.getUsed());
        // 当前内存剩余量
        systemMemoryCollect.setRemainingMemory(mem.getFree());

        // 交换区总量
        systemMemoryCollect.setTotalSwap(swap.getTotal());
        // 当前交换区使用量
        systemMemoryCollect.setUseSwap(swap.getUsed());
        // 当前交换区剩余量
        systemMemoryCollect.setRemainingSwap(swap.getFree());
        return systemMemoryCollect;
    }

    /**
     * 操作系统的cpu状态信息
     *
     * @return 操作系统的cpu状态信息
     * @throws SigarException 异常信息
     */
    public static SystemCpuGroupCollectCollect systemCpu() throws SigarException {

        Sigar sigar = new Sigar();
        CpuInfo[] infos = sigar.getCpuInfoList();
        CpuPerc[] cpuList;
        //CPU使用总量  没有格式化成百分比的信息
        //Cpu cpu = sigar.getCpu()
        //cpu 总百分比情况
        CpuPerc cpuPerc = sigar.getCpuPerc();
        //获取CPU的总消耗
        SystemCpuGroupCollectCollect cpuGroup = new SystemCpuGroupCollectCollect();
        //设置CPU组的基本状态
        setCpuInfo(cpuPerc, cpuGroup);
        //多核CPU下获取每一个核的使用状态
        cpuList = sigar.getCpuPercList();
        List<SystemCpuCollectCollect> systemCpusCollects = new ArrayList<>();

        // 不管是单块CPU还是多CPU都适用
        for (int i = 0; i < infos.length; i++) {
            SystemCpuCollectCollect systemCpuCollect = new SystemCpuCollectCollect();

            CpuInfo info = infos[i];
            systemCpuCollect.setCpuSort(i);
            // CPU的总量MHz
            systemCpuCollect.setCpuMhz(info.getMhz());
            // 获得CPU的卖主，如：Intel
            systemCpuCollect.setCpuSuper(info.getVendor());
            // 获得CPU的类别，如：Celeron
            systemCpuCollect.setCpuType(info.getModel());
            // 缓冲存储器数量
            systemCpuCollect.setCpuCacheCount(info.getCacheSize());
            setCpuInfo(cpuList[i], systemCpuCollect);
            systemCpusCollects.add(systemCpuCollect);
        }
        //将每一个核的使用情况放到组里面
        cpuGroup.setSystemCpus(systemCpusCollects);
        return cpuGroup;
    }

    /**
     * 总百分比情况
     *
     * @param cpuPerc       全核CPU的状态信息
     * @param systemCpuInfoCollect 系统CPU配置信息
     */
    private static void setCpuInfo(CpuPerc cpuPerc, SystemCpuInfoCollect systemCpuInfoCollect) {
        //总使用率
        double combined = cpuNumberFormat(cpuPerc.getCombined());
        systemCpuInfoCollect.setCpuTotal(combined);
        //用户使用率
        double user = cpuNumberFormat(cpuPerc.getUser());
        systemCpuInfoCollect.setCpuUserUse(user);
        //系统使用率
        double system = cpuNumberFormat(cpuPerc.getSys());
        systemCpuInfoCollect.setCpuSystemUse(system);
        //错误率
        double error = cpuNumberFormat(cpuPerc.getNice());
        systemCpuInfoCollect.setCpuError(error);
        //等待率
        double wait = cpuNumberFormat(cpuPerc.getWait());
        systemCpuInfoCollect.setCpuWait(wait);
        //空闲率
        double idle = cpuNumberFormat(cpuPerc.getIdle());
        systemCpuInfoCollect.setCpuIdle(idle);

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
    public static List<NetDataCollect> systemNetwork() throws Exception {
        List<NetDataCollect> netDatumCollects = new ArrayList<>(8);
        Sigar sigar = new Sigar();
        String[] interfaceNames = sigar.getNetInterfaceList();
        for (String interfaceName : interfaceNames) {
            NetDataCollect netDataCollect = new NetDataCollect();
            NetInterfaceConfig interfaceConfig = sigar.getNetInterfaceConfig(interfaceName);
            // 网络设备名
            netDataCollect.setNetworkName(interfaceName);
            // IP地址
            String address = interfaceConfig.getAddress();
            //过滤掉本机
            if("0.0.0.0".equals(address) || "127.0.0.1".equals(address)) {
                continue;
            }

            netDataCollect.setIpAddress(address);
            // 子网掩码
            netDataCollect.setSubnetMask(interfaceConfig.getNetmask());
            if ((interfaceConfig.getFlags() & 1L) <= 0L) {
                continue;
            }
            NetInterfaceStat ifstat = sigar.getNetInterfaceStat(interfaceName);
            // 接收的总包裹数
            netDataCollect.setReceivingDataPacketCount(ifstat.getRxPackets());
            // 发送的总包裹数
            netDataCollect.setSendDataPacketCount(ifstat.getTxPackets());
            // 接收到的总字节数
            netDataCollect.setReceivingDataByte(ifstat.getRxBytes());
            // 发送的总字节数
            netDataCollect.setSendDataByte(ifstat.getTxBytes());
            // 接收到的错误包数
            netDataCollect.setReceivingErrorDataPacketCount(ifstat.getRxErrors());
            // 发送数据包时的错误数
            netDataCollect.setSendErrorDataPacketCount(ifstat.getTxErrors());
            // 接收时丢弃的包数
            netDataCollect.setReceivingDiscardedDataPacketCount(ifstat.getRxDropped());
            // 发送时丢弃的包数
            netDataCollect.setSendDiscardedDataPacketCount(ifstat.getTxDropped());
            netDatumCollects.add(netDataCollect);
        }
        return netDatumCollects;
    }
}
