var vue = new Vue({
    el:'#app',
    data:{
        tableData: [],
        options: [],
        yearMonthInt: {},
    },
    created: function(){
        this.getTableData();
        this.getOptions();
    },
    methods:{
    	getOptions: function(){
            axios.get('exercise/historyRange')
            .then(res => {
            	this.options = res.data;
            })
            .catch(error => {
            	console.error('getOptions', error)
            })
        },
        changeYearMonth: function(yearMonthInt){
        	console.log('changeYearMonth:', yearMonthInt)
        	this.getTableData(yearMonthInt);
        },
        getTableData: function(yearMonthInt){
        	let params = {groupId: 2}
        	if (yearMonthInt) {
        		params.yearMonthInt = yearMonthInt
        	}
            axios.get('customer/statusList', {
            	params: params
            })
            .then(res => {
				let data = [];
				for (let i = 0; i < res.data.length; i++) {
					let item = {}
					item.id = res.data[i].customerPo.id
					item.username = res.data[i].customerPo.username
					item.fullName = res.data[i].customerPo.lastName + res.data[i].customerPo.firstName
					item.status = res.data[i].leave?'请假':''
					data[i] = item
				}
				this.tableData = data;
            })
            .catch(error => {
            	console.error('getTableData', error)
            })
        }, 
        takeLeave: function(id, leave){
            console.log('id', id);
            axios.get('customer/takeLeave', {
            	params: {
            		customerId: id, yearMonthInt: this.yearMonthInt, leave: leave
            	}
            })
            .then(res => {
                this.getTableData()
            })
            .catch(error => {
            	console.error('takeLeave', error)
            })
        }
    }
});