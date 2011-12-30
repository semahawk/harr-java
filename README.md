Harr!
=====

Harr! is an object-oriented, interpreted programming language built with help of Java (lexing, parsing, interpreting etc.), Ruby (tests) and a little bit of Bash (the bin file).

It's syntax is very similar to Ruby, but more Pirate-ish. Ayay!

System requirements
-------------------

+ Java 1.5+ ([download](http://www.java.com/en/download/index.jsp))
+ Ant 1.7+ ([download](http://ant.apache.org/))
+ Ruby 1.8+ ([download](http://www.ruby-lang.org/))

Getting sta'ted!
----------------

In order to get Harr! you would have to do several things.

Fo' first, tidy yer room, clean yer windows and get yerself a sandwich.

    clean --what windows --well --gotta-shine
    tidy room
    sudo make sandwich --with 'tomatoes,cheese,ham,letucce'

Stand up, clap 10 times, don't look behind ye, and t'en sneeze. Yer half way done!

T'en, obtain t'e source code by tapping on yer keyboard:

    git clone git://github.com/semahawk/harr.git

T'en, get in there!

    cd harr

G'job, me 'eartie! Now compile everything, run tests and the rest:

    ant

Simple, isn't it?

    bin/harr examples/guesswhat.harr

Runnin' without any arrrrgs would sta't the REPL.

    bin/harr


General language structure
---------------------------

+ Everything is an object
+ Each object (`HarrObject`) has a class (`HarrClass`)
+ Objects that relate to Java values (`String`, `Integer`, `Float`, etc.) are stored in a `ValueObject` instance
+ The lexer and parser grammars (the .g files) are compiled by [ANTLR](http://www.antlr.org/)
+ The parser creates custom nodes (under `src/harr/lang/nodes`) each one implementing the `eval` method
+ Each node is evaled on an instance of the `Context` class
+ Methods of `Harr` objects are created in `Bootstraper.java`

What directories are what?
--------------------------

<table>
<tr>
  <td><strong>Directory</strong></td>
  <td><strong>Content</strong></td>
</tr>
<tr>
  <td><pre>bin</pre></td>
  <td>Executables</td>
</tr>
<tr>
  <td><pre>examples</pre></td>
  <td>Example scripts written in Harr!</td>
</tr>
<tr>
  <td><pre>src</pre></td>
  <td>Harr! source files</td>
</tr>
<tr>
  <td><pre>test</pre></td>
  <td>Test files that are ran by <code>test/runner.rb</code> after running <code>ant</code> or <code>ant test</code>.</td>
</tr>
<tr>
  <td><pre>vendor</pre></td>
  <td>Contains additional sources like ANTLR and JLine</td>
</tr>
</table>  

A bit on syntax
---------------

### Comments

Comments are delimited by `%` sign.

    % Yo-ho-ho and a bottle of rum!

### Creating a method

    matey main
      rawr("Called main method")
    ends
    
    main

This will create a method [actually a matey, as.. you're calling your mateys :)] called `main`.
Then we call that matey, who rawrs "Called main method". He is quite weird, trust me.

### Creating a class

    ship QueenAnnesRevenge
      matey attack
        rawr("BOOM-BOOM!")
      ends
    ends
    
    % Making a new class
    revenge = QueenAnnesRevenge.new
    revenge.attack
    % => BOOM-BOOM!

As you can see, classes are ships. As they contain a lot of different things (including mateys). Cheers!

License
-------

Harr! is licensed under the MIT license (more info in the LICENSE file).
Use it however you wish!

Cheers!
