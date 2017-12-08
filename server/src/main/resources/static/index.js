new Vue({
    el:'#app',
    data:{
        tableData: []
    },
    created: function(){
        this.getTableData()
    },
    methods:{
        getTableData: function(){
            axios.get('customer/statusList', {
            	params: {
					groupId: 2
            	}
            })
            .then(res => {
              let data = [];
              for (let i = 0; i < res.data.length; i++) {
                  let item = {}
                  item.id = res.data[i].customerPo.id
                  item.username = res.data[i].customerPo.username
                  item.status = res.data[i].leave?'请假':''
                  data[i] = item
              }
              this.tableData = data;
            })
            .catch(e => {
            	console.error('error', e)
            })
        }, 
        takeLeave: function(id, leave){
            console.log('id', id);
            axios.get('customer/takeLeave', {
            	params: {
            		customerId: id, leave: leave
            	}
            })
            .then(res => {
                this.getTableData()
            })
            .catch(e => {
            	console.error('error', e)
            })
        }
    }
});