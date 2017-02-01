# Review Reporter

<p align="center">
  <img src="https://cdn-images-1.medium.com/max/720/1*YsnzNla1yxNoPV5cwIsbiw.png">
</p>

Review Reporter is a bot assistant that improves our interaction with users of Android applications.
It integrates Google Play, Slack, Jira and Firebase services together. 

Review Reporter performs in fixed interval ongoing actions:

1. Scans Google Play Store and fetches most recent reviews of your user concerning
your project.

2. Communicates with Firebase database to check which of fetched reviews were already
sent to Slack or Jira.

3. Converts every unsent user review to Slack message and posts it on our team channel. You have
access to review ids. Slack provides high variety of feature which you can include in messages.
Consequently each message has button attached to it which allows our team to respond to specific
review instantly, with a single click.

4. Converts every unsent user review with star rating less or equal 3 to Jira issue and assigns it 
to person responsible for customer service. That way problems our precious users won't ever
be omitted.

5. It updates Firebase with new ids so the same user review won't ever be reported twice.

## Related articles

  - [Review-Reporter: Part 1 - Connecting to Google Play](https://medium.com/azimolabs/review-reporter-part-1-connecting-to-google-play-8abd37edc49f#.rx06kjmup)

## Purpose of this repository 

As this project combines together many services, we came to conclusion that it might be 
a little bit hard to create general open-source version that would satisfy needs of all.
Maybe you are using other issue and progress tracking system than Jira. Or you are using 
different communication methods than Slack. Or you need to have other tools available in your
Slack messages. 

That's why this repo is only inspirational. So you can see how everything is connected together.
Furthermore we have created series of posts which will help you to connect with each
service separately, create your own Review Reporter and adjust it to your needs.

If you really want to use our version, then we have provided you with setup instructions. Feel
free to contact us if you don't understand something or you would like to give it a try but project 
would need some changes on our side.

## Setup

##### Config
All you need to do in order to make Review Reporter works for you is to fill
config file with proper values of your project.

```css
/* General info */
application_name application-name-on-google-play
android_package_name application-package

/* Google Play integration */
google_play_service_credentials_path ~/<credentials_dir>/credentialsGoogle.p12
google_play_service_account_email service-account
google_dev_console_acc google-dev-console-acc-to-access-review-replies

/* Jira integration */
jira_auth_credentials credentials-used-in-authorisation-header-in-jira-requests
jira_basepath base-path-to-your-jira-ended-with-slash
jira_project_name project-name
jira_issue_type issue-type
jira_assignee assignee-account-name
report_to_jira_scan_interval_millis scan-and-create-issues-interval

/* Firebase integration */
firebase_auth_credentials_path ~/<credentials_dir>/credentialsFirebase.json
firebase_basepath base-path-to-your-firebase-project-ended-with-slash

/* Slack integration */
slack_web_hook slack-web-hook
report_to_slack_scan_interval_millis scan-and-report-to-slack-interval
```
You shouldn't change any labels, only the values after "space" character. If you don't know
how to acquire/find any of the values, you can find more information in our tutorial article
series.

No one wants to keep credentials and keys, secrets in your project repository right? You can move 
[config](https://github.com/AzimoLabs/Review-Reporter/tree/master/config) folder outside of project and 
edit path to *.txt file in [config.properties](https://github.com/AzimoLabs/Review-Reporter/blob/master/config.properties).
Remember to use absolute paths (with ~).

##### Generating *.jar file
Project is built with gradle. 

1. Navigate to root of the project in your terminal/console.
2. Generate *.jar files by using `./gradlew shadowJar` command.
3. Your *.jar files will be created in `<project_root>/build/libs` location.
4. Use *.jar which has "assembly" in it's name e.g `review-reporter-2.6-assembly.jar`. 

##### Launching Review Reporter
Make sure your `config.properties` file which is pointing at your config file is in the same location
as jar file.

To launch Review Reporter use following command:

`java -jar JAR_NAME LAUNCHER_CLASS RUN_VARIANT` 

Where: 

 - <b>JAR_NAME</b>: depending on generated Review-Reported versions you might end up with various names. 
In example `review-reporter-2.6-assembly.jar`.

 - <b>LAUNCHER_CLASS</b>:: this value is always the same, it points to class which is launcher class of whole project. In our case use `com.azimo.tool.ReviewReporterService`.
 
 - <b>RUN_VARIANT</b>: two values available - `loopRun`, `singleRun`. We allow to run Review Reporter as task running in infinite loop (until stopped by user),
 or once per invocation (in case you wanted to loop it by yourself e.g. in Jenkins by using crone).  

##### Example of assembled run commands

 - `java -jar review-reporter-2.6-assembly.jar com.azimo.tool.ReviewReporterService singleRun` 
 - `java -jar review-reporter-2.6-assembly.jar com.azimo.tool.ReviewReporterService loopRun` 

## License

    Copyright (C) 2017 Azimo

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
