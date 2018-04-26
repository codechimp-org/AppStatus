# AppStatus [![How to include](https://jitpack.io/v/codechimp-org/AppStatus.svg)](https://github.com/codechimp-org/AppStatus#how-to-include)

An Android Library that will check a given URL for a JSON or XML file containing current service status.
Use the returned message and priority to display appropriate user information within your app.

Host your service status file (json or xml) wherever you like publicly so your app can check the current status.
If you have a backend you can automatically monitor simply publish the new file, otherwise do it by hand for known lengthy outages.

Inspired by [https://github.com/javiersantos/AppUpdater](https://github.com/javiersantos/AppUpdater) a great version checking solution.

[![API](https://img.shields.io/badge/API-14%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=14)

## How to include
Add the repository to your project **build.gradle**:
```Gradle
repositories {
    maven {
        url "https://jitpack.io"
    }
}
```

And add the library to your module **build.gradle**:
```Gradle
dependencies {
    compile 'com.github.codechimp-org:AppStatus:1.0.0'
}
```

## Usage

```Java
AppStatus appStatus = new AppStatus(this)
        .setUpdateFormat(UpdateFrom.JSON)
        .setUpdateURL("https://raw.githubusercontent.com/codechimp-org/AppStatus/master/samplestatus.json")
        .withListener(new AppStatus.StatusListener() {
            @Override
            public void onSuccess(Status status) {
                // Handle displaying your message and priority here
                Log.d(TAG, status.getMessage());
            }

            @Override
            public void onFailed(AppStatusError error) {
                // Do your error handling here, could be incorrect URL, bad json or xml, or network issue
            }
        });

appStatus.start();
```

See the sample app for a larger example using the SnackBar.

## License
	Copyright 2018 CodeChimp
	
	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
	   http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.