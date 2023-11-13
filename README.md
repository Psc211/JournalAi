# JournalAi

My team (TicTechBoom) has won the 2nd place at this hackaton, making us very proud. Here are the details:

Together with 3 other team members, I've developed an Android application which uses an AI chatbot in order to track three main skills which we chose, as a team, such that the user can interact with the chatbot in a natural manner, and all the processing is done 'behind the scenes'.

I have personally been responsible with the the implementation of the OpenAi API, design, planning and general problem solving.

After the login system, you can directly chat to the model and, based on your responses, the 'trained' OpenAI API will give back 2 different responses, for 2 different threads:

First thread gives a response to the user, on the front end.
The second thread gives the backend a JSON object, under the form of [Skill:Hours] such that we could process it and add it to the database, in case it finds any in the user's prompt.

The app is supposed to be like a journal for your day, while also keeping track of the skills you do everyday.

Tools: Android Studio, Chart library, SQLite, OpenAI API.
