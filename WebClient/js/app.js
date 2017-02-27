/* Configuration */
Vue.use(VueResource);
Vue.use(VueRouter);
const APIBaseURL = 'http://localhost:8080/shop/services/shopservice';
var currency = 'EUR';

/* Components */
// Home
const Home = {
  template: '\
  <div class="ui one column stackable centered page grid">\
			<div class="column twelve wide">\
			  <h1 class="ui dividing header"> Shop Application </h1>\
			  <div class="ui raised segment">\
          <p> Welcome to the Shop Application. You can either browse the product catalog and buy \
          a product or check the status of your bank account. </p>\
          <h4> Please select a preferred currency </h4>\
          <select v-model="currency">\
            <option value="EUR">EUR</option>\
            <option value="GBP">GBP</option>\
            <option value="USD">USD</option>\
          </select>\
          &nbsp;\
          <button class="ui tiny primary button" @click="save"> Save </button>\
        </div>\
      </div>\
  </div>',
  data: function() {
    return {
      currency: 'EUR'
    };
  },
  methods: {
    save: function() {
      currency = this.currency;
      this.selectedCurrency = this.currency;
      console.log('Currency updated ' + this.currency + '/' + currency);
      document.getElementById('currency').innerHTML = this.currency;
    }
  }
}

// Catalog
const Catalog = {
  	template: 
	'<div class="ui centered stackable cards">\
    <!-- Feedback Message -->\
    <div v-if="feedback.show"\
         class="ui message" v-bind:class="feedback.result">\
      <div class="header"> {{feedback.result | capitalize}} </div>\
      <p> {{feedback.message}} </p>\
    </div>\
		<router-link v-for="(product, index) in products" class="ui link card" v-bind:to="product.link">\
      <div class="image">\
        <img v-bind:src="product.image">\
      </div>\
			<div class="content">\
				<div class="header"> {{product.name}} </div>\
				<div class="description">\
					<p> {{product.description}} </p>\
				</div>\
			</div>\
			<div class="extra content">\
				<span class="left floated">\
					Price: {{product.unitPrice}} \
				</span>\
				<span class="right floated">\
					Quantity: {{product.remainingQuantity}}\
				</span>\
			</div>\
		</router-link>\
	</div>',
	data: function() {
		return {
			products: [],
      feedback: {
        show: false,
        result: 'Error',
        message: '' 
      }
		}
	},
	// Retrieve the product catalog
	mounted: function() {
		this.$http.get(APIBaseURL + '/products?currency=' + currency).then(function(response) {
			console.log('GET /getallproducts > Success');
			this.products = response.data;
			// Add link for the router
			for(var i=0;i<this.products.length; ++i)
				this.products[i].link = '/products/' + this.products[i].id;
		}, function(response) {
			console.log('GET /products > Error');
        this.feedback.show = true;
        this.feedback.result = 'error';
        this.feedback.message = 'Cannot reach API';
		});
	},
  filters: {
    capitalize: function (value) {
      if (!value) return ''
      value = value.toString()
      return value.charAt(0).toUpperCase() + value.slice(1)
    }
  }
};

// Product
const Product = {
	template: 
  '<div class="ui one column stackable centered page grid">\
			<div class="column twelve wide">\
        <!-- Feedback Message -->\
        <div v-if="feedback.show"\
             class="ui message" v-bind:class="feedback.result">\
          <div class="header"> {{feedback.result | capitalize}} </div>\
          <p> {{feedback.message}} </p>\
        </div>\
				<!-- Product details -->\
				<h4 class="ui dividing header"> Product </h4>\
				<div class="ui items">\
					<div class="item">\
						<div class="image">\
							<img v-bind:src="product.image">\
						</div>\
						<div class="content">\
							<a class="header"> {{product.name}} </a>\
							<div class="meta">\
								<span> Unit price: {{product.unitPrice}} </span>\
							</div>\
							<div class="description">\
								<p> {{product.description}} </p>\
							</div>\
						</div>\
					</div>\
				</div>\
				<!-- Authentication form -->\
				<form class="ui form">\
          <h4 class="ui dividing header"> Order </h4>\
          <div class="two fields">\
            <div class="field">\
              <b>Quantity:</b> {{quantity}} \
              <div class="ui mini icon buttons">\
                <button class="ui button" @click="decQuantity">\
                  <i class="minus icon"></i>\
                </button>\
                <button class="ui mini button" @click="incQuantity">\
                  <i class="plus icon"></i>\
                </button>\
              </div>\
            </div>\
            <div class="field">\
              <b>Price:</b> {{price}}\
            </div>\
          </div>\
					<h4 class="ui dividing header"> Account </h4>\
					<div class="two fields">\
						<div class="field">\
							<label> Username </label>\
							<input v-model="username" placeholder="Username" type="text">\
						</div>\
						<div class="field">\
							<label> Password </label>\
							<input v-model="password" placeholder="Password" type="password">\
						</div>\
					</div>\
          <div @click="purchase" class="ui right floated primary button"> Purchase </div>\
        </form>\
      </div>\
		</div>',
	data: function() {
		return {
			product: { link: '' },
      quantity: 0,
      username: '',
      password: '',
      feedback: {
        show: false,
        result: 'Error',
        message: '' 
      }
		}
	},
  computed: {
    price: function() {
      var price = this.quantity * this.product.unitPrice;
      return price + '€ (+delivery fee)';
    }
  },
  methods: {
    incQuantity: function() {
      if(this.quantity<this.product.remainingQuantity)
        ++this.quantity;
    },
    decQuantity: function() {
      if(this.quantity>0)
        --this.quantity;
    },
    // Purchase API call
    purchase: function() {
      var body = {
        account: {
          username: this.username,
          password: this.password
        },
        itemID: this.product.id,
        quantity: this.quantity
      };
    	this.$http.post(APIBaseURL + '/products/' + this.product.id + '/buy?currency=' + currency, body).then(function(response) {
			  console.log('POST /products/' + this.product.id + '/buy > Success');
        this.feedback.show = true;
        this.feedback.result = response.data.success ? 'success' : 'error';
        this.feedback.message = response.data.message;
		  }, function(response) {
			  console.log('POST /products/' + this.product.id + '/buy > Error');
        this.feedback.show = true;
        this.feedback.result = 'error';
        this.feedback.message = 'Cannot reach API';
		  });
    }
  },
	// Retrieve the product
	mounted: function() {
		var id = this.$route.params.id;
		this.$http.get(APIBaseURL + '/products/' + id + '?currency=' + currency).then(function(response) {
			console.log('GET /products/' + id + ' > Success');
			this.product = response.data;
      this.product.link = '/purchase/' + response.data.id;
		}, function(response) {
			console.log('GET /products/' + id + ' > Error');
      this.feedback.show = true;
      this.feedback.result = 'error';
      this.feedback.message = 'Cannot reach API';
		});
	},
  filters: {
    capitalize: function (value) {
      if (!value) return ''
      value = value.toString()
      return value.charAt(0).toUpperCase() + value.slice(1)
    }
  }
};

// Account
const Account = {
  template: '\
  <div class="ui one column stackable centered page grid">\
			<div class="column twelve wide">\
			  <!-- Feedback Message -->\
        <div v-if="feedback.show"\
             class="ui message" v-bind:class="feedback.result">\
          <div class="header"> {{feedback.result | capitalize}} </div>\
          <p> {{feedback.message}} </p>\
        </div>\
        <h4 class="ui dividing header"> Account </h4>\
        <div v-if="!connected" class="ui form">\
					<div class="two fields">\
						<div class="field">\
							<label> Username </label>\
							<input v-model="username" placeholder="Username" type="text">\
						</div>\
						<div class="field">\
							<label> Password </label>\
							<input v-model="password" placeholder="Password" type="password">\
						</div>\
					</div>\
          <div @click="getAccount" class="ui right floated primary button"> Get account </div>\
        </div>\
        <div v-else>\
          <ul class="ui list"> \
            <li> <b>Username:</b> {{ account.username }} </li>\
            <li> <b>Firstname:</b> {{ account.firstname }} </li>\
            <li> <b>Lastname:</b> {{ account.lastname }} </li>\
            <li> <b>Amount:</b> {{ account.amount }} </li>\
            <li> <b>Address</b> \
              <ul class="ui list">\
                <li> <b>Street:</b> {{ account.address.street }} </li>\
                <li> <b>City:</b> {{ account.address.city }} </li>\
                <li> <b>Country:</b> {{ account.address.country }} </li>\
              </ul>\
            </li>\
          </ul>\
        </div>\
      </div>\
  </div>',
  data: function() {
    return {
      connected: false,
      username: '',
      password: '',
      feedback: {
        show: false,
        result: 'Error',
        message: '' 
      },
      account: {}
    };
  },
	methods: {
	  // Retrieve the bank account
	  getAccount: function() {
		  var body = {
		    username: this.username,
		    password: this.password
		  };
		  console.log('Account: ' + body.username + ' / ' + body.password);
		  this.$http.post(APIBaseURL + '/account?currency=' + currency, body).then(function(response) {
			  console.log('POST /account/' + ' > Success');
			  this.account = response.data;
        this.connected = true;
        this.feedback.show = false;
		  }, function(response) {
			  console.log('POST /account/' + ' > Error');
        this.feedback.show = true;
        this.feedback.result = 'error';
        this.feedback.message = 'Cannot reach API';
		  });
	  }
	},
  filters: {
    capitalize: function (value) {
      if (!value) return ''
      value = value.toString()
      return value.charAt(0).toUpperCase() + value.slice(1)
    }
  }
};

/* Router */
const router = new VueRouter({
	routes: [
	  { path: '/', name: 'home', component: Home },
		{ path: '/products', name: 'catalog', component: Catalog },
		{ path: '/products/:id', name: 'product', component: Product },
		{ path: '/account', name: 'account', component: Account },
	]
});

/* App */
const app = new Vue({
	router: router,
	data: {}
}).$mount('#app');

