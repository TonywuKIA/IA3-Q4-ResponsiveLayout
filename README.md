# IA3 – Q4 Responsive Layout

## Overview
This project demonstrates a responsive master–detail layout built using Jetpack Compose and Material 3.

The UI adapts based on screen width:

- **Phone mode (narrow screen):** single column layout
- **Tablet / landscape mode (wide screen):** two-pane layout with a persistent navigation pane on the left and detail content on the right

A width breakpoint (~700dp) is used to switch between layouts.

---

## Implementation Details

### Layout Behavior
- `BoxWithConstraints` detects screen width
- `Column` used for phone layout
- `Row` used for tablet layout
- Left pane: options list
- Right pane: detail content

### Material 3 Components Used
- TopAppBar
- NavigationRail
- ListItem
- Card / ElevatedCard
- AssistChip
- IconButton

### Modifiers Demonstrated
- `weight()` for pane sizing
- `fillMaxHeight()` for side panel
- `LazyColumn` for scrollable list

---

## Screenshots
**Phone mode (<700dp):**  
The UI uses a single vertical column where the options list appears above the detail content.
<img width="434" height="918" alt="image" src="https://github.com/user-attachments/assets/628766b9-24cc-41c6-b3fc-0da8e0b4e1dc" />



**Tablet / Wide mode (≥700dp):**  
The layout adapts based on screen width (~700dp breakpoint).
The UI switches to a two-pane layout. The navigation/options list stays on the left while the detail panel remains visible on the right.
<img width="883" height="419" alt="image" src="https://github.com/user-attachments/assets/34b728ee-3733-4499-83d5-e1408fdf6c13" />



Screenshots demonstrating both layouts are included in the submission.

## How to Test
1. Run the app
2. Rotate emulator to landscape
3. Layout switches to two-pane mode

---

## AI Usage Disclosure
AI assistance (ChatGPT) was used for debugging layout behavior and understanding Compose responsive patterns. Final implementation and testing were completed manually.
