# GetPassword

PURPOSE
A password generator is a common feature of password managers and even many modern browsers. When combined with a password manager, the random strings these programs produce offer a high level of security against brute force attacks even when the method of generation is known by the attacker. This purpose of this program is to generate passwords with a quality that is comparable to the aforementioned conventional password generators. Since GetPassword is a custom implementation, it has the added benefit of creating passwords that are extra secure against attackers who haven’t read this repository :)

METHOD
GetPassword uses a cryptographically secure pseudorandom number generator as supplied by the SecureRandom class. Specifically, the program uses the SHA1PRNG algorithm as provided by Sun. 

FEATURES
GetPassword is designed to be called in shell and takes one to three arguments

$GetPassword length [return option] [symbols]

length: positive integer
return option: [0-2] 0: (Default) prints password to stdout and copies to clipboard. 1: Prints to stdout. 2:Copies to clipboard
symbols: String of special characters that can be used in password. Program only recognizes characters that exist in the default string. Defaults to: !*&@%+\\/'!#$^?:,(){}[]~`_ 

