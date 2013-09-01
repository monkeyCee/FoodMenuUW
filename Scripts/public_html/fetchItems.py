import json, urllib2, urllib

data = json.load(urllib2.urlopen('http://api.uwaterloo.ca/public/v2/foodservices/2013/11/menu.json?key=4aa5eb25c8cc979600724104ccfb70ea'));
outlets = data['data']['outlets']
for location in outlets:
#	print location['outlet_name'];
	for day in location['menu']:
#		print day['day'];
#		print "Lunch";
		for items_lunch in day['meals']['lunch']:
#			print "Product:		", items_lunch['product_name'];
#			print "Product ID:	", items_lunch['product_id'];	
#			print "Product Diet:	", items_lunch['diet_type'];
			if items_lunch['product_id'] is not None:
				params = {'restaurant': location['outlet_name'], 'day': day['day'], 'meal': 'Lunch', 'item': items_lunch		['product_id']};
			else:
				params = {'restaurant': location['outlet_name'], 'day': day['day'], 'meal': 'Lunch', 'item': '0', 'product': 					items_lunch['product_name']};
			print items_lunch['product_name'];
			parameters = urllib.urlencode(params);
			request = urllib2.Request('http://localhost/insertRestaurantItems.php', parameters);
			response = urllib2.urlopen(request);
  			print response.read();
#		print "\n";
#		print "Dinner";
		for items_dinner in day['meals']['dinner']:
#			print "Product:		", items_dinner['product_name'];
#			print "Product ID:	", items_dinner['product_id'];	
#			print "Product Diet:	", items_dinner['diet_type'];
			if items_dinner['product_id'] is not None:
				params = {'restaurant': location['outlet_name'], 'day': day['day'], 'meal': 'Dinner','item': items_dinner['product_id']};
			else:
				params = {'restaurant': location['outlet_name'], 'day': day['day'], 'meal': 'Dinner','item': '0', 'product':items_dinner['product_name']};
			parameters = urllib.urlencode(params);
			request = urllib2.Request('http://localhost/insertRestaurantItems.php', parameters);
			response = urllib2.urlopen(request);	
  			print response.read();
#		print "\n";
		
	
