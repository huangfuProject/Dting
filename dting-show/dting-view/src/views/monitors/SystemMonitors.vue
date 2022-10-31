<template>
    <div>
        <!--系统内存监控折线图-->
        <div><LineChart lineChartTitle="系统内存监控" :systemXAxisArray="systemXAxisArray" :systemUse="systemUseMemory" :systemMax="systemMaxMemory" height="300px" width="400px"/></div>
        <div><LineChart lineChartTitle="JVM内存监控" :systemXAxisArray="systemXAxisArray" :systemUse="systemUseMemory" :systemMax="systemMaxMemory"  height="300px" width="400px"/></div>
        <div><LineChart lineChartTitle="系统Swap监控" :systemXAxisArray="systemXAxisArray" :systemUse="systemUseMemory" :systemMax="systemMaxMemory" height="300px" width="400px"/></div>
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
            //------------------------------------系统内存监控数据---------------------------------------------------------------
            systemXAxisArray:[],
            systemUseMemory:[],
            systemMaxMemory:[]
            //------------------------------------jvm内存监控数据---------------------------------------------------------------
            //------------------------------------swap监控数据---------------------------------------------------------------
        }
    },
    methods:{
        //可以将一个数据转换为图标需要的数据格式
        monitorsMemoryDataHandler(obj) {
            if (typeof obj == 'string') {
                obj = JSON.parse(obj); 
            }
            // x轴坐标展示
            this.systemXAxisArray.push(obj.dateValue)
            // 内存的使用数据
            this.systemUseMemory.push(obj.useSystemMemory)
            //内存的最大值
            this.systemMaxMemory.push(obj.maxSystemMemory)
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
        // 将后端的内存数据解包为图标需要数据格式
        refreshSystemMemoryData(res){
            const memoryDataList = res.memoryDataVo.systemMemoryDataList
            if(memoryDataList) {
                for(var memoryData of memoryDataList) {
                    //开始将内存数据映射到图表
                    this.monitorsMemoryDataHandler(memoryData)
                }

            }
        }
    },
    created: function(){
        this.initMemoryData()
    },
    components:{
        LineChart
    }
}
</script>
