<template>
    <div>
        <!--系统内存监控折线图-->
        <div><EchartsPackage :options="systemMemoryOption"/></div>
    </div>
</template>

<script>
import EchartsPackage from '../../components/echarts/EchartsPackage.vue'
import {initWebSocket, sendData} from '../../utils/webSocket'
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
        //websocket返回的消息的处理器
        monitorsDataHandler(obj) {
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
        //开始连接对象
        connectWebsocket(obj){
            initWebSocket(obj)
        },
        //发送消息后设置回调
        sendDataMessage(data){
            sendData(data, this.monitorsDataHandler)
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
                const memoryDataList = res.memoryDataVo.systemMemoryDataList
                if(memoryDataList) {
                    for(var memoryData of memoryDataList) {
                        //调用消息回调
                        this.monitorsDataHandler(memoryData)
                    }

                }
            })
        }
    },
    computed: {
        /**
         * 系统内存监控数据
         */
        systemMemoryOption(){
            return {
                title: {
                text: '系统内存监控'
                },
                tooltip: {
                    trigger: 'axis'
                },
                legend: {
                data: ['系统总内存','系统已经使用的内存']
                },
                xAxis: {
                    type: 'category',
                    show: true,
                    boundaryGap: false,
                    data: this.systemXAxisArray,
                    axisLabel: {
                        interval: 2
                    }
                },
                yAxis: {
                    type: 'value'
                },
                series: [
                    {
                        name: '系统总内存',
                        type: 'line',
                        step:true,
                        data: this.systemMaxMemory,
                        showSymbol: false
                    },
                    {
                        name: '系统已经使用的内存',
                        type: 'line',
                        step:false,
                        data: this.systemUseMemory,
                        showSymbol: false
                    }
                ]
            }
        }
    },
    created: function(){
        this.initMemoryData()
        //连接
        //this.connectWebsocket(),
        //发送一个初始的查询条件
        // this.sendDataMessage({
        //     sessionId:"1234567890",
        //     purpose:"0",
        //     serverEnv:"dev",
        //     serverKey:"test-Server",
        //     instanceKey:"test-server-001",
        //     startTime:1666829350124,
        //     endTime:-1
        // })
    },
    components:{
        EchartsPackage
    },
    watch: {
        systemXAxisArray(){
            //动态变更首尾数据
            this.systemMemoryOption.xAxis.axisLabel.interval=this.systemXAxisArray.length-2
        }
    }
}
</script>
