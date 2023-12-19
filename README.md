# BeFitOutfit Application

## Overview

Welcome to our AI Fashion Assistant, an innovative Android application designed to revolutionize
your wardrobe choices and enhance your style effortlessly. Our app leverages artificial intelligence
to provide personalized outfit suggestions tailored to various events, helping you boost confidence,
save time, and express your unique style with ease.

## Features

- **Outfit Classification** - Our app uses a trained model to classify the user's camera input into
  14 categories: concert, graduation, meeting, mountain trip, picnic, sea holiday, ski holiday,
  wedding, conference, exhibition, fashion, protest, sport, and theater-dance. The user can then
  choose to save the outfit to their outfit list.

- **Add Outfit** - Users can add outfits by taking a picture of their outfit and providing a
  description of the outfit. The outfit is then stored in the user's outfit list.

- **Outfit Recommendation** - Our app provides outfit recommendation based on the user's input of
  the event type and their own outfit.

- **Login/Register** - Users can create an account and login to their account to access their outfit
  list.

## Installation & Setup

1. Clone the repository
2. Open the project in Android Studio
3. Add this to your `local.properties` file:
    ```
    MOCK="true"         # or "false" if you want to use the real API
    BASE_URL=""         # base URL of the real API
    BASE_URL_MOCK=""    # base URL of the mock API
    ```
4. Add [this model](https://github.com/Capstone-Bangkit-BeFitOutfit/BeFitOutfit-ML/blob/main/model.tflite)
to `app/src/main/ml/` directory with the name `BeFitOutfitModel.tflite`
5. Run the app
