import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: ()=>import("../views/Home.vue")
  },{
      path: '/system/monitors',
      name: "SystemMonitors",
      component: ()=>import("../views/monitors/SystemMonitors.vue")
  }
]

const router = new VueRouter({
  mode:'history',//去端口#号,
  routes
})

export default router
