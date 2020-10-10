 private void requestLocationUpdates() {
        LocationRequest request = new LocationRequest();
        request.setInterval(3000);
        request.setFastestInterval(3000);

        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
         client = LocationServices.getFusedLocationProviderClient(this);

        int permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);


        if (permission == PackageManager.PERMISSION_GRANTED) {


            client.requestLocationUpdates(request,
                    callback= new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {

                    Location loc = locationResult.getLastLocation();
                    if (loc != null) {
                        location location1 = new location(loc.getLatitude(), loc.getLongitude());

                        BaseApiService mApiService;
                        mApiService = UtilsApi.getAPIService();
                        ping ping = new ping(driver_id, location1);
                        mApiService.update(access_token, ping).enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.isSuccessful()) {

                                    Log.d("LocationUpdateService", "=============> successful");
                                } else {

                                    //  Toast.makeText(getApplicationContext(), " failure " + response.message(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Log.d("debug", "onFailure: ERROR > " + t.toString());
                            }
                        });

                    }
                }
            }, null);
        }
    }