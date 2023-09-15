Vue.createApp({
    data() {
        return {
            accountInfo: {},
            errorToats: null,
            errorMsg: null,
        }
    },
    methods: {
        getData: function () {
            const urlParams = new URLSearchParams(window.location.search);
            const id = urlParams.get('id');

            axios.get(`/api/accounts/${id}`)
                .then((response) => {
                    //get client ifo
                    this.accountInfo = response.data;
                    console.log(this.accountInfo);
                    this.accountInfo.transactions.sort((a, b) => (b.id - a.id))
                })
                .catch((error) => {
                    // handle error
                    this.errorMsg = "Error getting data";
                    this.errorToats.show();
                })
        },
        formatDate: function (date) {
            return new Date(date).toLocaleDateString('en-gb');
        },
        signOut: function () {
            axios.post('/api/logout')
                .then(response => window.location.href = "/web/index.html")
                .catch(() => {
                    this.errorMsg = "Sign out failed"
                    this.errorToats.show();
                })
        },
        getResume: function () {
            const urlParams = new URLSearchParams(window.location.search);
            const id = urlParams.get('id');
            const start = document.getElementById('fromDate');
            const end = document.getElementById('toDate');
            console.log(end.value);

              axios.get(`/api/generate-report?id=${id}&start=${start.value}&end=${end.value}`,{ responseType: 'blob' })
                .then((response) => {
                    const blob = new Blob([response.data], { type: 'application/pdf' });
                    const contentDisposition = response.headers['content-disposition'];
                    const matches = contentDisposition.match(/filename=([^;]+)/);
                    const filename = matches ? matches[1] : 'download.pdf';
                    const url = window.URL.createObjectURL(blob);
                    // Create a temporary anchor element to trigger the download
                    const a = document.createElement('a');
                    a.href = url;
                    a.download = filename;
                    // Trigger the download by simulating a click on the anchor element
                    a.click();
                })
                    .catch((error) => {
                    // handle error
                    this.errorMsg = "Error getting data";
                    this.errorToats.show();
            })

        },
    },
    mounted: function () {
        this.errorToats = new bootstrap.Toast(document.getElementById('danger-toast'));
        this.getData();
    }
}).mount('#app')