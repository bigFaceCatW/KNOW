
//------------------------------------------------全局注册--------------------------------------------
Vue.component('button-count',{ //一个组件的模板必须具备一个根节点！
    props:['title'],
    data:function () {
        return{
            count:0
        }
    },
    template:'<div><button @click="countAdd">{{title}}数字加{{count}}</button></div>',
    methods:{
        countAdd:function () {
            this.count++;
            this.$emit('mount-add',this.count);
        }
    }
})
Vue.component('button-todo',{ //一个组件的模板必须具备一个根节点！
    props:['todo','todoCase'],
    template:'<li>{{todo.name}}:{{todoCase}}</li>'

})

Vue.component('base-checkbox', {
    model: {
        prop: 'checked',
        event: 'change'
    },
    props: {
        checked: Boolean
    },
    template: `<input type="checkbox" v-bind:checked="checked" v-on:change="$emit('change', $event.target.checked)">`
})
//----------------------------局部注册----------------------------------------
var local = {
    data: function () {
        return {
            num: this.id
        }
    },
    props: {
        id:Number,
        name:String,
        date:Object
    },
    template:'<div> <button @click="change">点击增加{{num}}:{{id}};名称:{{name}}转化后名称：{{name}}</button></div>',
    methods:{
        change:function () {
            this.num++;
        },
        computed: {
            nameSize: function () {
                return this.name.trim().toLowerCase()
            }
        }
        }

}


var vm = new Vue({
    el: '#app',
    data: {
        picked:2,
        select:'',
        checkboxName: [],
        message: 'Hello',
        use:false,
        id:'userId',
        judgment:false,
        url:'http://www.runoob.com',
        object: {
            name: '菜鸟教程',
            url: 'http://www.runoob.com',
            slogan: '学的不仅是技术，更是梦想！'
        },
        count:1,
        kilometers:0,
        meter:0,
        firstName:'首名字',
        lastName:'尾名字',
        products:[]
    },
    created:function(){//1.钩子
       fetch("/request/getData")
           .then(response => response.json())
           .then(json => {
               this.products = json.products
           })
    },
    components:{
        'com-btn':local
    },
    computed:{ //2.计算属性：调用时直接使用methodQuery|用于侦听器 会有缓存
        totalProducts:function(){
            return this.products.reduce((sum,product) =>{
                return sum + product.value;
            },0)
        },
        methodAttr:function(){
            alert("触发methodAttr计算属性");
            return this.message + "methodAttr测试中";
        },
        now: function () {
            return Date.now()
        },
        integration:function () {
            return this.firstName + this.lastName;
        },
        set: function (newValue) {
            var names = newValue.split(' ')
            this.firstName = names[0]
            this.lastName = names[names.length - 1]
        }
    },
    methods:{  //3.computed 性能更好，效果一样，调用时：methodQuery(),不希望缓存就用methods
        mountAdd:function(param){
            alert(param);
        },
        methodQuery:function(){
            alert("触发method方法");
            return this.message + "测试中";

        },
        doSomething:function(event){
            alert(this.message)
            if (event) {
                alert(event.target.tagName)
            }
        },
        change:function(){
            this.message = this.message.split('').reverse().join('')
            // var names = newValue.split('')空格分隔
        }
    },
    watch:{ //4.监听值的变化
        kilometers:function(val){
            this.kilometers=val;
            this.meter = this.kilometers * 1000;
        },
        meter:function(val){
            this.meter=val;
            this.kilometers = this.meter/1000;
        }

    },
    filters:{//5.拦截器
        capitalize: function(value){
            if(value=='Hello'){
                return "测试1"
            }else{
                return value.toString + "测试2"
            }

        }
    }

});

vm.$watch('kilometers',function (newValue,oldValue) {  //与4效果一样
    $("#mybatis").html('新值：'+newValue+'------'+'旧值：'+oldValue)
    this.kilometers=newValue;
    this.meter = this.kilometers * 1000;
})


$(function(){


});







