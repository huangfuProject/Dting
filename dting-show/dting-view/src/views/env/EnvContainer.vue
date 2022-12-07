<template>
    <div>
        <el-container>
            <el-header>
                <el-row :gutter="20">
                    <el-col :span="4">
                        <div id="logoDiv"> 
                            <b>
                                Dting(比你更了解你的系统)
                            </b>
                        </div>
                    </el-col>
                    <el-col :span="18">
                        <div id="selePrectGroup">
                            <el-row :gutter="20">
                                <el-col :span="8">
                                    <div id="serverOption">
                                        服务信息：
                                        <el-select v-model="serverValue" :filterable=true clearable placeholder="请选择" @change="serverValueChange" @focus="loadServerData">
                                            <el-option v-for="service in services" :key="service.value" :label="service.label" :value="service.value"/>
                                        </el-select>
                                    </div>
                                </el-col>

                                <el-col :span="8">
                                    <div id="envOption">
                                        环境信息：
                                        <el-select v-model="envValue" :filterable=true clearable placeholder="请选择" @change="envValueChange" @focus="loadEnvData">
                                            <el-option v-for="item in envs" :key="item.value" :label="item.label" :value="item.value"/>
                                        </el-select>
                                    </div>
                                </el-col>


                                <el-col :span="8">
                                    <div id="envOption">
                                        实例信息：
                                        <el-select v-model="instanceValue" :filterable=true clearable placeholder="请选择" @change="instanceValueChange" @focus="loadInstance">
                                            <el-option v-for="item in instances" :key="item.value" :label="item.label" :value="item.value"/>
                                        </el-select>
                                    </div>
                                </el-col>
                                
                            </el-row>
                        </div>
                        
                    </el-col>
                    <el-col :span="2">
                        <div id="userLink">
                            <el-dropdown>
                                <span class="el-dropdown-link">
                                    userName<i class="el-icon-arrow-down el-icon--right"></i>
                                </span>
                                <el-dropdown-menu slot="dropdown">
                                    <el-dropdown-item icon="el-icon-view">账号设置</el-dropdown-item>
                                    <el-dropdown-item icon="el-icon-minus">退出登录</el-dropdown-item>
                                </el-dropdown-menu>
                            </el-dropdown>
                        </div>
                    </el-col>
                </el-row>
            </el-header>
            <el-main>Main----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------</el-main>
        </el-container>
    </div>
</template>

<script>
    import {post} from '../../utils/request'

    export default {
        name: "EnvContainer",
        data() {
            return {
                //环境信息
                envs: [],
                //服务信息
                services:[],
                //实例信息
                instances:[],
                //选中的环境值
                envValue: '',
                //选中的服务值
                serverValue:'',
                //选中的实例
                instanceValue:'',
            }
        },
        methods:{
            /**
             * 加载服务信息
             */
             loadServerData(){
                this.serverDataSelectReSet();
                this.envDataSelectReSet();
                this.instanceDataSelectReSet()
                let thiObj = this
                //请求服务信息
                post('/server/findServerList', {}).then(res =>{
                    const data = res.result;
                    for(var server of data) {
                        //将服务信息推送到服务列表
                        thiObj.services.push({
                            value:server.id,
                            label:server.serverName
                        })

                    }
                })
            },
            /**
             * 加载环境信息
             */
            loadEnvData(){
                if(this.serverValue) {
                    //重置环境信息
                    this.envDataSelectReSet()
                    this.instanceDataSelectReSet()
                    let thisObj = this
                    //请求环境信息
                    post('/env/findAllDtingEnv', {
                        "serverId": thisObj.serverValue
                    }).then(res =>{
                        const data = res.result;
                        for(var env of data) {
                            thisObj.envs.push({
                                value:env.id,
                                label:env.envName
                            })

                        }
                    })
                }
            },
            /**
             * 加载实例信息
             */
            loadInstance(){
                if(this.envValue) {
                    //重置实例信息
                    this.instanceDataSelectReSet()
                    let thisObj = this
                    //请求实例信息
                    post('/instance/findInstanceList', {
                        "envId": thisObj.envValue
                    }).then(res =>{
                        const data = res.result;
                        for(var instance of data) {
                            thisObj.instances.push({
                                value:instance.id,
                                label:instance.instanceName
                            })

                        }
                    })
                }
                
            },
            /**
             * 服务信息重置
             */
            serverDataSelectReSet(){
                this.serverValue = "";
                this.services.length = 0;
            },
            /**
             * 环境信息重置
             */
            envDataSelectReSet(){
                this.envValue = "";
                this.envs.length = 0;
            },
           /**
             * 实例信息重置
             */
            instanceDataSelectReSet(){
                this.instanceValue = "";
                this.instances.length = 0;
            },

            /**
             * 
             * @param {*} newValue 服务信息选中的值
             */
             serverValueChange(newValue){
                if(newValue != "") {
                    this.loadEnvData()
                }else {
                    this.serverDataSelectReSet()
                    this.envDataSelectReSet()
                    this.instanceDataSelectReSet()
                }
            },

            /**
             * 
             * @param {*} newValue 环境信息选中的值
             */
            envValueChange(newValue){
                if(newValue != "") {
                    this.loadInstance()
                } else {
                    this.envDataSelectReSet()
                    this.instanceDataSelectReSet()
                }
            },
            
            /**
             * 实例数据变更
             * @param {*} newValue 
             */
            instanceValueChange(newValue) {
                if(newValue  != "") {
                    console.log(newValue)
                }else {
                    this.instanceDataSelectReSet()
                }
            }
            
        },
        //钩子函数
        created: function(){
            
        }
    }
</script>
<style>
    .el-header {
        background-color: #B3C0D1;
        color: #333;
        text-align: center;
        line-height: 60px;
    }

    #selePrectGroup {
        float: right;
    }

    #envOption {
        float: right;
    }

    #serverOption {
        float: left;
    }

    #userLink {
        float: right;
    }

    #userLink {
        float: right;
    }
    #logoutLink {
        float: center;
    }

    #logoDiv {
        float: left;
    }

    .el-dropdown-link {
        cursor: pointer;
        color: #000000;
    }
</style>