<template>
    <div>
        <!--系统内存监控折线图-->
        <div><LineChart lineChartTitle="系统内存使用趋势" :xAxisArray="systemXAxisArray" :legendData="memoryLegendData" :serviceData="systemMemoryServiceData"/></div>
        <!--jvm内存监控折线图-->
        <div><LineChart lineChartTitle="JVM内存使用趋势" :xAxisArray="systemXAxisArray"  :legendData="memoryLegendData" :serviceData="jvmMemoryServiceData"/></div>
        <!--swap内存监控折线图-->
        <div><LineChart lineChartTitle="系统Swap使用趋势" :xAxisArray="systemXAxisArray" :legendData="memoryLegendData" :serviceData="swapMemoryServiceData"/></div>
        <!--cpu使用折线图-->
        <div><LineChart lineChartTitle="系统CPU使用趋势(%)" :xAxisArray="systemCpuXAxisArray" :legendData="cpuLegendData" :serviceData="cpuServiceData"/></div>

        <div>
            <GaugeChart :serviceData="systemMemoryGaugeData"></GaugeChart>
            <GaugeChart :serviceData="jvmMemoryGaugeData"></GaugeChart>
            <GaugeChart :serviceData="swapMemoryGaugeData"></GaugeChart>
            <GaugeChart :serviceData="cpuMemoryGaugeData"></GaugeChart>
        </div>
    </div>
</template>

<script>
import LineChart from '../../components/echarts/LineChart.vue'
import GaugeChart from '../../components/echarts/GaugeChart.vue'
import {initWebSocket, setCallback} from '../../utils/webSocket'
import {post} from '../../utils/request'
import {guid} from '../../utils/idUtil'
export default {
    name: "SystemMonitors",
    data(){
        return {
            // 监控的id
            sessionId:guid(),
            memoryLegendData:["总量","已使用"],
            cpuLegendData:["CPU总使用率","CPU用户使用率","CPU系统使用率","CPU等待率","CPU错误率"], 
            
            //------------------------------------系统内存监控数据---------------------------------------------------------------
            systemXAxisArray:[],
            systemUseMemory:[],
            systemMaxMemory:[],
            //------------------------------------jvm内存监控数据------------------------------------------------------------
            jvmUseMemory:[],
            jvmMaxMemory:[],
            //------------------------------------swap监控数据---------------------------------------------------------------
            swapUseMemory:[],
            swapMaxMemory:[],
            //------------------------------------CPU数据--------------------------------------------------------------
            //时间数据
            systemCpuXAxisArray:[],
            //cpu的总使用数据
            cpuTotalUse:[],
            //cpu的用户使用数据
            cpuUserUes:[],
            //cpu的系统使用数据
            cpuSystemUes:[],
            //cpu的等待率
            cpuWait:[],
            //cpu的错误率
            cpuError:[]
        }
    },
    methods:{
        //可以将一个数据转换为图标需要的数据格式
        monitorsMemoryDataHandler(obj, type) {
            if (typeof obj == 'string') {
                obj = JSON.parse(obj); 
            }
            if(type === 'system') {
                // x轴坐标展示
                this.systemXAxisArray.push(obj.dateValue)
                // 内存的使用数据
                this.systemUseMemory.push(obj.useSystemMemory)
                //内存的最大值
                this.systemMaxMemory.push(obj.maxSystemMemory)
            } else if(type === 'jvm') {
                // jvm内存的使用数据
                this.jvmUseMemory.push(obj.useJvmMemory)
                // jvm内存的最大值
                this.jvmMaxMemory.push(obj.maxJvmMemory)
            } else if(type === 'swap') {
                // swap内存的使用数据
                this.swapUseMemory.push(obj.useSystemSwap)
                // swap内存的最大值
                this.swapMaxMemory.push(obj.maxSystemSwap)
            }else if(type === 'cpu') {
                this.systemCpuXAxisArray.push(obj.dateValue)
                //时间维度的数据
                this.cpuTotalUse.push(obj.totalUse)
                // cpu的用户使用数据
                this.cpuUserUes.push(obj.userUse)
                // cpu的系统使用数据
                this.cpuSystemUes.push(obj.systemUse)
                // cpu的等待率
                this.cpuWait.push(obj.wait)
                // cpu的错误率
                this.cpuError.push(obj.error)
            }

        },
        //websocket 消息回调
        websocketMessageCallback(obj){
            if (typeof obj == 'string') {
                obj = JSON.parse(obj); 
            }
            //内存数据
            if(obj.type === '0') {
                const bodyObj = obj.body
                this.refreshSystemMemoryData(JSON.parse(bodyObj));
            }else if(obj.type === '1') {
                const bodyObj = obj.body
                this.refreshSystemCpuData(JSON.parse(bodyObj))
            }
        },
        //开始连接对象
        connectWebsocket(obj){
            initWebSocket(obj);
            setCallback(this.websocketMessageCallback)
        },
        //初始化内存数据
        initMemoryData(){
            let obj = this.objParam;
            //post方式请求后台获得初始化的表表数据
            post('/memory/memoryMonitoring', obj).then(res =>{
                const data = res.result;
                this.refreshSystemMemoryData(data);
                const monitorId = data.monitorId;
                this.connectWebsocket(monitorId);
            })
        },
        // 初始化cpu数据
        initCpuData(){
            let obj = this.objParam;
            post('/cpu/cpuMonitoring', obj).then(res =>{
                const data = res.result;
                this.refreshSystemCpuData(data);
            });
        },

        // 将后端的内存、jvm内存、交换区数据解包为图标需要数据格式
        refreshSystemMemoryData(res){
            //系统内存数据
            const memoryDataList = res.systemMemoryDataList
            if(memoryDataList) {
                for(var memoryData of memoryDataList) {
                    //开始将内存数据映射到图表
                    this.monitorsMemoryDataHandler(memoryData, 'system')
                }
            }
            //jvm内存数据
            const jvmMemoryDataList = res.jvmMemoryDataList
            if(jvmMemoryDataList) {
                for(var jvmMemoryData of jvmMemoryDataList) {
                    //开始将内存数据映射到图表
                    this.monitorsMemoryDataHandler(jvmMemoryData, 'jvm')
                }
            }
            //交换区数据
            const systemSwapDataList = res.systemSwapDataList
            if(systemSwapDataList) {
                for(var systemSwapData of systemSwapDataList) {
                    //开始将内存数据映射到图表
                    this.monitorsMemoryDataHandler(systemSwapData, 'swap')
                }
            }
            
        },
        //刷新CPU数据
        refreshSystemCpuData(res) {
            const systemCpuDataList = res.systemCpuDataList
            if(systemCpuDataList) {
                for(var systemCpuData of systemCpuDataList) {
                    this.monitorsMemoryDataHandler(systemCpuData, 'cpu')
                }
                
            }
        }
    },
    created: function(){
        this.initMemoryData()
        this.initCpuData()
    },
    components:{
        LineChart,
        GaugeChart
    },
    computed: {
        objParam() {
                return {
                    instanceKey:"test-server-001",
                    serverEnv:"dev",
                    serverKey:"test-Server",
                    startTime:1666920510100,
                    endTime:-1,
                    sessionId:this.sessionId
                }
            },
        /**
         * 系统使用比率
         */
        systemUseProportion(){
            const max = this.systemMaxMemory[this.systemMaxMemory.length - 1]
            const use = this.systemUseMemory[this.systemUseMemory.length - 1]
            return Math.floor((use/max)*100)
        },
        /**
         * jvm使用比率
         */
         jvmUseProportion(){
            const max = this.jvmMaxMemory[this.jvmMaxMemory.length - 1]
            const use = this.jvmUseMemory[this.jvmUseMemory.length - 1]
            return Math.floor((use/max)*100)
        },
        /**
         * SWAP使用比率
         */
         swapUseProportion(){
            const max = this.swapMaxMemory[this.swapMaxMemory.length - 1]
            const use = this.swapUseMemory[this.swapUseMemory.length - 1]
            return Math.floor((use/max)*100)
        },
        /**
         * CPU使用比率
         */
         cpuUseProportion(){
            const use = this.cpuTotalUse[this.systemUseMemory.length - 1]
            return use
        },
        /**
         * 系统内存折线图数据
         */
        systemMemoryServiceData(){
            return [{
                        name:'总量',
                        type:'line',
                        areaStyle: {},
                        step:true,
                        data:this.systemMaxMemory,
                        showSymbol:false
                    },
                    {
                        name:'已使用',
                        type:'line',
                        areaStyle: {},
                        step:false,
                        data:this.systemUseMemory,
                        showSymbol:false
                    }]
        },
        /**
         * jvm折线图数据
         */
        jvmMemoryServiceData(){
            return [{
                        name:'总量',
                        type:'line',
                        areaStyle: {},
                        step:true,
                        data:this.jvmMaxMemory,
                        showSymbol:false
                    },
                    {
                        name:'已使用',
                        type:'line',
                        areaStyle: {},
                        step:false,
                        data:this.jvmUseMemory,
                        showSymbol:false
                    }
                ]
            },
        /**
         * jvm折线图数据
         */
        swapMemoryServiceData(){
            return [{
                        name:'总量',
                        type:'line',
                        areaStyle: {},
                        step:true,
                        data:this.swapMaxMemory,
                        showSymbol:false
                    },
                    {
                        name:'已使用',
                        type:'line',
                        areaStyle: {},
                        step:false,
                        data:this.swapUseMemory,
                        showSymbol:false
                    }]
        },
        cpuServiceData(){
            return [{
                        name:'CPU总使用率',
                        type:'line',
                        step:true,
                        data:this.cpuTotalUse,
                        showSymbol:false
                    },
                    {
                        name:'CPU用户使用率',
                        type:'line',
                        step:true,
                        data:this.cpuUserUes,
                        showSymbol:false
                    },
                    {
                        name:'CPU系统使用率',
                        type:'line',
                        step:true,
                        data:this.cpuSystemUes,
                        showSymbol:false
                    },
                    {
                        name:'CPU等待率',
                        type:'line',
                        step:true,
                        data:this.cpuWait,
                        showSymbol:false
                    },
                    {
                        name:'CPU错误率',
                        type:'line',
                        step:true,
                        data:this.cpuError,
                        showSymbol:false
                    }]
        },
        systemMemoryGaugeData(){
            const obj = this
            return [
                {
                    value: obj.systemUseProportion,
                    name:"系统内存"
                }
            ]
        },
        jvmMemoryGaugeData(){
            const obj = this
            return [
                {
                    value: obj.jvmUseProportion,
                    name:"jvm内存"
                }
            ]
        },
        swapMemoryGaugeData(){
            const obj = this
            return [
                {
                    value: obj.swapUseProportion,
                    name:"swap内存"
                }
            ]
        },
        cpuMemoryGaugeData(){
            const obj = this
            return [
                {
                    value: obj.cpuUseProportion,
                    name:"CPU"
                }
            ]
        }
    }
}
</script>
