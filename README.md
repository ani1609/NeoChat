# NeoChat - Team AsyncAwait
>A modern chat app that puts inclusivity first and allows seamless conversation between the disabled and others.

## Android app 🔽

<a href="https://github.com/ani1609/NeoChat/releases/download/v1.0.0/neo-chat.apk"><img alt="Get it on GitHub" src="https://user-images.githubusercontent.com/69304392/148696068-0cfea65d-b18f-4685-82b5-329a330b1c0d.png" height=80px />

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
