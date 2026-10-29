# Number Guessing Game — Cross-platform standalone packages

A Java Swing desktop game packaged separately for Windows, macOS, and Linux. The GitHub Actions workflow uses `jpackage` to bundle a Java runtime with the app, so players do **not** need to install Java or compile source code.

## How to get downloadable executables

This repository includes a GitHub Actions workflow at `.github/workflows/package.yml`.

1. Create a new **public** GitHub repository.
2. Upload all files and folders from this project, including the hidden `.github` folder.
3. Commit and push the files.
4. Open the repository's **Actions** tab. Run **Build self-contained desktop packages** with **Run workflow** to build packages. The resulting workflow run has downloadable artifacts.
5. To publish permanent release downloads, create and push a version tag from your local Git repository:
   ```bash
   git tag v1.0.0
   git push origin v1.0.0
   ```
   The workflow will build all three platform packages and attach them to a GitHub Release.

## What players download

- **Windows:** `NumberGuessingGame-Windows-x64.zip` — extract it and launch `NumberGuessingGame.exe` inside the app folder.
- **macOS:** `NumberGuessingGame-macOS.zip` — extract it and open `NumberGuessingGame.app`.
- **Linux:** `NumberGuessingGame-Linux-x64.tar.gz` — extract it and launch `NumberGuessingGame/bin/NumberGuessingGame`.

Each app image includes its own Java runtime. Users don't need a JDK and do not compile anything.

## Requirements for maintainers

GitHub Actions builds each package on its corresponding operating system. This matters because native app images are OS-specific. The workflow uses JDK 21 and `jpackage`.

## Game features

- Random number from 1 to 100
- Seven valid guesses per round
- High/low hints
- Guess history, win counter, and replay button
- Input validation

## Source

Main source: `src/NumberGuessingGameGUI.java`

This project is for learning and demonstration. On macOS, first-launch security prompts may appear because the app is not code-signed/notarized. Linux desktop environments may require execute permission after extraction.
