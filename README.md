# Live odds services

## My thought process

### Creating a project

As I understand main goal of this recruitment process is to know candidate. So I decided to write here my thoughts.
    
At start, I initiated project and added git. Then created project structure and required classes. There are finite amount
of countries with clearly defined names, so putting them in the enum class seems to be the most logical decision,
at least in terms of my code exercise. For real project I could add all ~200 countries,
but for code exercise demonstration purposes I guess countries from example will be enough. 
For clean code purposes I added lombok to project to avoid writing boilerplate code.

### Writing tests

I assumed, that one country can have only one team in given circumstances, so there cannot be 
match with same countries.

After some time I finally understood, why it's taking me so long, to move forward. From one side, there is code exercise
with clearly defined requirement and guidance to keep code as simple as possible, which means nothing redundant.
On the other side, not everything can be put in the instruction. Some things are just implied to be.
Because of this "I need to do something, I don't know what" I was going in circles. Even, though, it wasn't
explicitly required, I added constructors and getters\setters for every field of Score, Match and Scoreboard 
entities with default values for optional fields. Also, wasn't specified which type of data some of described
operations return, so I choose it myself.

So, at first I begin to write tests covering every assumption and requirement I had in code
exercise description. It's a good practise to not commit a code, that is not compiling, so
then in the same commit I added all required methods without actual code.

Ok, now some programmer routine. For some reason tests just wouldn't run. After some time I figured out, 
that I should've deleted project physically from my device and pull It again from GitHub. I guess that was 
some funny ideas from Intellij idea. 

After that I rather quickly was able to implement all code and pass all tests.

### Implementing last feature

Okay, I thought that I finished, but I forgot last thing. Get summary of matches. Again, lets start by making tests
according to requirements. 

For now, I'm wondering, what is the best way to write this test. I can manually check positions of 
all matches in scoreboard, or I can write right sorting algorithm in test and then check for equality.
I decided to write a sorting algorithm.

So, i guess there is some mistake in exercise description, or it's just unclear in which order 
matches from first example is given. Because in first example I thought the most recent matches go
from a to e. But in sorted example Uruguay - Italy go before Spain - Brazil. Anyway, I added my 
custom start date in test cases to be sure everything is correct. Results in debugger showed matches
sorted from highest score to lowest and from most recent to most past.

### Reread my code

I see some problems. I have LiveOddsServiceTest, but my LiveOddsService is empty. All business logic currently is in 
ScoreBoard entity. From one side, it's wrong. Entities are for keeping values. Fields, getters, setters and no business logic.
From the other side, required operations are quite simple. Getting and setting values for Scoreboard objects. I'm not sure
if I should somehow put it out to LiveOddsService.

Then, usually tests are written for services, because services have business logic. I cant remember a case, where methods
of entities would be tested. But some of my validation are built-in in setters and constructors
and i'm not sure how to treat them.

Reread solid principles. A principe of single responsibility. My scoreboard is printing out already sorted list of matches.
Is it a violation of single responsibility? On the one side in exercise description clearly described, 
that "the scoreboard supports the following operations:", so it's looks logical to keep it inside this class. 

I guess my project file structure with jpa, modules and entities is for REST APIs/microservices solutions.
Maybe i should treat it all a bit easier. I checked how libraries imported from maven look like.

Finally i decided, I will delete unnecessary service and main classes. Also I will divide tests in different classes.
The moment I stopped treating Match, Score and Scoreboard as entities everything became a lot clearer.
