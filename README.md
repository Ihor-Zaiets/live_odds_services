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
