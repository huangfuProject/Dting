<template>
    <div>
        <!--系统内存监控折线图-->
        <div><LineChart lineChartTitle="系统内存监控" :xAxisArray="systemXAxisArray" :legendData="memoryLegendData" :serviceData="systemMemoryServiceData"/></div>
        <!--jvm内存监控折线图-->
        <div><LineChart lineChartTitle="JVM内存监控" :xAxisArray="systemXAxisArray"  :legendData="memoryLegendData" :serviceData="jvmMemoryServiceData"/></div>
        <!--swap内存监控折线图-->
        <div><LineChart lineChartTitle="系统Swap监控" :xAxisArray="systemXAxisArray" :legendData="memoryLegendData" :serviceData="swapMemoryServiceData"/></div>
    </div>
</template>

<script>
import LineChart from '../../components/echarts/LineChart.vue'
import {initWebSocket, setCallback} from '../../utils/webSocket'
import {post} from '../../utils/request'
export default {
    name: "SystemMonitors",
    data(){
        return {
            memoryLegendData:["总量","已使用"],
            
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
            }

        },
        //websocket 消息回调
        websocketMessageCallback(obj){
            if (typeof obj == 'string') {
                obj = JSON.parse(obj); 
            }
            //内存数据
            if(obj.type === '0') {
                var bodyObj = obj.body
                bodyObj = JSON.parse(bodyObj); 
                this.refreshSystemMemoryData(bodyObj);
            }
        },
        //开始连接对象
        connectWebsocket(obj){
            initWebSocket(obj);
            setCallback(this.websocketMessageCallback)
        },
        //初始化内存数据
        initMemoryData(){
            var obj = {
                instanceKey:"test-server-001",
                serverEnv:"dev",
                serverKey:"test-Server",
                startTime:1666920510100,
                endTime:-1
            }
            post('/memory/memoryMonitoring', obj).then(res =>{
                this.refreshSystemMemoryData(res);
                const monitorId = res.monitorId;
                this.connectWebsocket(monitorId);
            })
        },
        // 将后端的内存、jvm内存、交换区数据解包为图标需要数据格式
        refreshSystemMemoryData(res){
            //系统内存数据
            const memoryDataList = res.memoryDataVo.systemMemoryDataList
            if(memoryDataList) {
                for(var memoryData of memoryDataList) {
                    //开始将内存数据映射到图表
                    this.monitorsMemoryDataHandler(memoryData, 'system')
                }
            }
            //jvm内存数据
            const jvmMemoryDataList = res.memoryDataVo.jvmMemoryDataList
            if(jvmMemoryDataList) {
                for(var jvmMemoryData of jvmMemoryDataList) {
                    //开始将内存数据映射到图表
                    this.monitorsMemoryDataHandler(jvmMemoryData, 'jvm')
                }
            }
            //交换区数据
            const systemSwapDataList = res.memoryDataVo.systemSwapDataList
            if(systemSwapDataList) {
                for(var systemSwapData of systemSwapDataList) {
                    //开始将内存数据映射到图表
                    this.monitorsMemoryDataHandler(systemSwapData, 'swap')
                }
            }
            
        }
    },
    created: function(){
        this.initMemoryData()
    },
    components:{
        LineChart
    },
    computed: {
        /**
         * 系统内存折线图数据
         */
        systemMemoryServiceData(){
            return [{
                        name:'总量',
                        type:'line',
                        step:true,
                        data:this.systemMaxMemory,
                        showSymbol:false
                    },
                    {
                        name:'已使用',
                        type:'line',
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
                        step:true,
                        data:this.jvmMaxMemory,
                        showSymbol:false
                    },
                    {
                        name:'已使用',
                        type:'line',
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
                        step:true,
                        data:this.swapMaxMemory,
                        showSymbol:false
                    },
                    {
                        name:'已使用',
                        type:'line',
                        step:false,
                        data:this.swapUseMemory,
                        showSymbol:false
                    }]
        }
    }
}
</script>
