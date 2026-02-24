# IA3 – Q3 Tag Browser

## Overview
This project implements a Tag Browser screen using Jetpack Compose. The UI demonstrates responsive wrapping layouts using FlowRow and FlowColumn together with interactive Material 3 chips.

## Implementation
- FlowRow displays all tags as FilterChips that wrap automatically based on screen width
- FlowColumn is used in the selected tags section to organize chosen items vertically
- Users can tap chips to select/unselect tags and clear all selections

## Material 3 Components
FilterChip, 
AssistChip, 
Card, 
TextButton, 
Divider

## Layout & Modifiers
- Consistent spacing using Arrangement.spacedBy
- Responsive sizing using fillMaxWidth and padding
- Visual selected state using color and border changes

Screenshots

-Initial screen


<img width="402" height="863" alt="image" src="https://github.com/user-attachments/assets/5a68cd54-2e78-4d90-b241-c53f7492962e" />

-Selected tags


<img width="400" height="848" alt="image" src="https://github.com/user-attachments/assets/16661657-7894-48c2-8783-49741bad0eda" />

-Wrapped layout on small width


<img width="872" height="393" alt="image" src="https://github.com/user-attachments/assets/cb070a1b-39d6-4abb-8caf-d81e0e512fc9" />

-With only selected


<img width="381" height="838" alt="image" src="https://github.com/user-attachments/assets/69bc0ad9-5bdf-485d-90c8-d81f484a66b3" />

## AI Usage Disclosure
AI was used as a learning aid to understand Compose concepts and to help debug Gradle configuration issues, and generated partial of readme. All final implementation decisions and code were written and verified by me. 
