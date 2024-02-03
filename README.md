# NeoChat - Team AsyncAwait
Hackathon project for Bit-N-Build 2024 Hackathon


## High level working of the app

1. The user authenticates and provides information.

2. The user then is able to send text, image and voice messages to others.

3. On the receiver’s side, the processing happens. If the receiver is
- Deaf: All audio files sent to the receiver is transcribed to text
which shows up a as a regular text message.
- Color-blind: All image files go through a color filter to assist
them with visibility. It aims to cater 7 different conditions:
deuteranopia, protanopia, tritanopia, tritanomaly,
deuteranomaly, cone monochromacy and rod
monochromacy.
- Blind: All text messages sent to the receiver is synthesized to
speech which shows up a voice message.

![BridgeTogether DFD](https://github.com/ani1609/NeoChat/assets/124783808/84ab96b1-841d-4efc-b87e-5dc5c20e535b)
