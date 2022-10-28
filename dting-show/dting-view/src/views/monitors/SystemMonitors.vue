<template>
    <div>
        <!--系统内存监控折线图-->
        <div><EchartsPackage :options="systemMemoryOption"/></div>
    </div>
</template>

<script>
import EchartsPackage from '../../components/echarts/EchartsPackage.vue'
import {initWebSocket, sendData} from '../../utils/webSocket'
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
        monitorsDataHandler(e) {
            var data = e.data
            //格式化为对象
            var obj = JSON.parse(data); 
            // x轴坐标展示
            this.systemXAxisArray.push(obj.dateValue)
            // 内存的使用数据
            this.systemUseMemory.push(obj.systemUseMemory)
            //内存的最大值
            this.systemMaxMemory.push(obj.systemMaxMemory)
        },
        //开始连接对象
        connectWebsocket(obj){
            initWebSocket(obj)
        },
        //发送消息后设置回调
        sendDataMessage(data){
            sendData(data, this.monitorsDataHandler)
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
                        interval: 2,
                        //rotate: -60,//60 标签倾斜的角度
                        // formatter: function (params) {
                        //     let newParamsName = '';
                        //     const paramsNameNumber = params.length; // 文字总长度
                        //     const provideNumber = 4; //一行显示几个字
                        //     const rowNumber = Math.ceil(paramsNameNumber / provideNumber);
                        //     if (paramsNameNumber > provideNumber) {
                        //     for (let p = 0; p < rowNumber; p++) {
                        //         const start = p * provideNumber;
                        //         const end = start + provideNumber;
                        //         const tempStr = p === rowNumber - 1 ? params.substring(start, paramsNameNumber) : params.substring(start, end) + '\n';
                        //         newParamsName += tempStr;
                        //     }
                        //     } else {
                        //         newParamsName = params;
                        //     }
                        //     return newParamsName;
                        // }

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
        //连接
        this.connectWebsocket(),
        //发送一个初始的查询条件
        this.sendDataMessage({
            sessionId:"1234567890",
            purpose:"0",
            serverEnv:"dev",
            serverKey:"test-Server",
            instanceKey:"test-server-001",
            startTime:1666829350124,
            endTime:-1
        })
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
