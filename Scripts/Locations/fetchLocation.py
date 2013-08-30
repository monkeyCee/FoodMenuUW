import json, requests

timing = "";
response = requests.get('http://api.uwaterloo.ca/public/v1/?key=4aa5eb25c8cc979600724104ccfb70ea&service=FoodServices&output=json')
data = json.loads(response.text)
result = data['response']['data']['result']
for location in result:
	timing = "";
	print ""
	print "Location:", location['Location']
	print "ID:", location['ID']
	for timings in location['Hours']['result']:
		timing += timings + "\n";
	print "Timings:", timing
	if(location['ID'] == "20" or location['ID'] == "22"):
		params = {'id': location['ID'], 'location': location['Name'],'timings': timing}
	else:
		params = {'id': location['ID'], 'location': location['Location'],'timings': timing}
	response1= requests.post("http://localhost/create.php",data=params);
	print response1.text



