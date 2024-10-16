# My_app

What is my app about?
The app I want to develop, consist basically a fishing app. The thing that I mean with this concept, is that I want my program to be used by the people that do fishing as a sport, the practical use of the app is to track all the catches that are done during the fishing day, by this way they could add each fish and add information about it to take into account for future days.

Domain details:
The domains that I have in mind are a couple, the first one would be the day you went fishing and inside of it you will find more domains about each fish. Therefore, inside a day you find all the fishes that you added during the day and each of them have different fields, which are: 
1. Name: You write in that field the name of the species that was caught.
2. Weight: It contains the weight of each fish.
3. Fishing bait: It could be helpful for the user to see clearly how effective is each bait that he uses.
4. Place: Its good to remember where you caught the best fishes or where you found more for future days.
5. Photo: Having a photo of each catch for the user to have a good way of remembering some of the catches he did.


Now speaking about the CRUD operations:
1. Create: You can first create a day, there you can put todays date or another, then that would open a new window where you can create a fish cath where you fill the fields about that fish that you caught that are mentioned in the domain details. However, when you add a fish you can leave some fields empty and fill them later, for example you if cant weigh the fish in that moment.
2. Read: At the moment you open the app, you see a list of all the days that have been created, if you select one of them a window is opened where you see all the fish caught that day, in each of them you see the details of each fish that were added in the past.
3. Update: You can update all the different fields that appeared, for example changing the date, the name of the fish or fill a field that was empty because it was not filled at the moment of the catch.
4. Delete: You can delete two things, a whole day so you get rid of all the information that was added in that day or delete a single fish, so from all the fish of a day only the one that you deleted would dissapear with the information you wrote about it.

Persistence details:
For my program, I need to have all the crud operations persisted local. The reason that I want all operations to be local is that for this app working offline is really important, many fishing places doesn't have connection to internet. 
On the other side, I want to have on the server the Create, Update and Delete.
1. Create: If I want my data to be stored in both places, I need that when i create the data of a fish or a day to be created in the two of them.
By this way when the data of a fish is created from other device, it will be stored in the server and from the server to the other devices that have the account.
2. Read: We only need to read locally, the reason is that when we have connection the changes made from other places also affect our local memory, so we dont need to read in the server when the data is in our device.
4. Update: Its really important that when an update is done that it affects both areas, by this way all the updates done from different devices can be seen in each of them by the server.
5. Delete: We need to take into account that if we dont have the delete operation on the server, that when we delete something its only deleted on the local db so when we read something and we have connection we will still having the data that was supposedly deleted.


What happens when its offline?
Many places that are good for fishing arent connected, for example the high seas or the deep forests.
If we dont have connection, all the operations will work one the local db and when we have the connection again the server data will be updated. For each CRUD operation:
1. Create: When we create a fish or a day it will be stored in both places but if it doesn't have connection, then it will only be on the local db and be uploaded to the server when you are connected again.
2. Read: We only read the local db so we only look at the stored information in our device, this means that we will only see the data of the fishes that were stored from the past fishing days and the new ones that we add or update from then.
3. Update: Every update made since the device is offline on the information about the fishes caught, will do the update only on the local db. By the moment you are online, the updates that you did will be done on the server.
4. Delete: When you delete something from the app, it will dissapear from the local db, but it will persist on the server until you are online again.





















