# KazakhCharsView
[![](https://jitpack.io/v/askarsyzdykov/kazakhcharsview.svg)](https://jitpack.io/#askarsyzdykov/kazakhcharsview)

## Requirements
* Minimum SDK version: 16

## Usage

Step 1. Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
Step 2. Add the dependency

	dependencies {
	        compile 'com.github.askarsyzdykov:kazakhcharsview:0.1'
	}
<img src="/screenshots/Screenshot_1524750953.png" alt="Drawing" width="300px"/>

The following properties are available:

| property name                      | type              | description                                                         |
| ---------------------------------- | ----------------- | ------------------------------------------------------------------- |
| app:kcv_allCaps                    | boolean           | Present the text in ALL CAPS.                                       |
| app:kcv_fontSize                   | dimension         | Size of the text.                                                   |
| app:kcv_textColor                  | color or resource | Text color.                                                         |
| app:kcv_type                       | enum              | cyrillic or latin, e.g. Ә for cyrillic or Á for latin               |
