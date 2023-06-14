<p align="center">
  <img src="https://github.com/Ndy-S/Drone-Capstone-Repo/blob/main/Mobile%20Development/DropDone/app/src/main/res/drawable/ic_drone.png" alt="Drone (Drop & Done Documentation)">
</p>

<h3 align="center">Drone (Drop & Done) Capstone Project Documentation</h3>

<div align="center">
  
  ![GitHub contributors](https://img.shields.io/github/contributors/Ndy-S/Drone-Capstone-Repo)
  ![Last commit](https://img.shields.io/github/last-commit/Ndy-S/Drone-Capstone-Repo)
  ![GitHub stars](https://img.shields.io/github/stars/Ndy-S/Drone-Capstone-Repo?style=social)
  ![GitHub forks](https://img.shields.io/github/forks/Ndy-S/Drone-Capstone-Repo?style=social)
  
</div>

---

This repository contains the source code and resources for our capstone project a laundry app that offers on-demand laundry services. Developed as part of a combined learning path in Machine Learning, Cloud Computing, and Mobile Development.

## Table of Contents
- [Introduction](#introduction)
	- [Project Overview](#project-overview)
	- [Motivation](#motivation)
- [Technologies Used](#technologies-used)
- [Architecture](#architecture)
- [Features](#features)
  - [Map Display and Interactive Interface](#map-display-and-interactive-interface)
  - [Order Tracking](#order-tracking)
  - [Sentiment Analysis](#sentiment-analysis)
  - [Recommendation System](#recommendation-system)
  - [Additional Features](#additional-features)
- [Machine Learning](#machine-learning)
- [Mobile Development](#mobile-development)
- [Cloud Computing](#cloud-computing)
- [Deployment](#deployment)

## Introduction
### Project Overview
Our capstone project aims to transform the laundry service industry using technology to provide efficient and convenient laundry solutions. We have developed an Android app that incorporates machine learning and natural language processing (NLP) techniques to offer personalized recommendations for laundry services based on individual preferences and needs. By analyzing user activity and reviews, we can understand customer preferences and adjust our recommendations accordingly. The app also allows users to track the progress of their laundry orders, providing transparency and convenience. We have integrated the app with Google Cloud and Firebase to ensure secure data management and efficient access.

### Motivation
In Indonesia, small and medium-sized enterprises (SMEs) play a crucial role in the economy. Among these SMEs, the laundry service industry serves busy individuals who value saving time and energy. However, traditional laundry methods are often inefficient, resulting in inaccurate estimates and dissatisfied customers. Recognizing this problem, our team was motivated to use technology as a solution for improving laundry services.

## Technologies Used
Our capstone project uses the following tools, IDE, Library, and Frameworks:
* Android Studio
* Jetpack Compose
* JSON
* TensorFlow
* Emoji
* NumPy
* Pandas
* Matplotlib
* Sastrawi
* Scikit-learn
* Google Cloud Platform
* Google Maps API
* Firebase

## Architecture
<img src="https://github.com/Ndy-S/Drone-Capstone-Repo/blob/main/architecture_diagram.png" alt="Architecture Diagram" width="700">


## Features
### Map Display and Interactive Interface
The app can display a map interface using the Google Maps API, allowing users to visualize the location of laundry services, their current position, and other relevant landmarks. Users can interact with the map by zooming, panning, and exploring different areas.

### Order Tracking
You can see where your laundry order is and how long it will take to complete. This makes it easy for you to know when your laundry will be ready.

### Sentiment Analysis
We use TensorFlow to train sentiment analysis models, enabling the app to understand and analyze user reviews and feedback. This sentiment analysis helps in generating more accurate recommendations.

### Recommendation System
Our app uses Natural Language Processing (NLP) techniques to analyze user feedback and reviews. Based on this analysis, recommendations for laundry services are provided to each user.

### Additional Features
The app also lets you leave comments and ratings for laundry services, book appointments, and quickly search for what you need. These features make using the app more enjoyable and convenient.

## Machine Learning
* Data Preprocessing: Before training the sentiment analysis models and recommendation systems, we perform data preprocessing tasks to handle missing values, remove duplicate values, and ensure data quality.
* Sentiment Analysis with TensorFlow: We use TensorFlow to train sentiment analysis models. These models are trained on a labeled dataset, enabling the app to predict the sentiment scores of user reviews.
* Recommendation System: We create a recommendation system that suggests personalized laundry services to users. We use two techniques, cosine similarities and matrix factorization.
  * Cosine Similarities: Cosine similarity measures how similar two things are. In our case, we calculate the similarity between user preferences and laundry service attributes. By finding similar services based on user preferences, we can recommend relevant options.
  * Matrix Factorization: Matrix factorization helps us identify patterns and preferences in user behavior and laundry service characteristics. It allows us to make predictions and fill in missing information to generate accurate recommendations.

## Mobile Development
* Jetpack Compose and UI Design: Our app's interface and how users interact with it are built using Jetpack Compose, a modern toolkit for creating Android apps.
* Google Authentication with Firebase: To provide secure user login, we integrate Google Authentication using Firebase.
* Google Maps API Integration: We use the Google Maps API to include location-based features in our app.

## Cloud Computing
* Firestore Database Setup: We create a database on the Google Cloud Platform to store information like user activity and reviews.
* Secure Access and Permissions: We make sure that only authorized people can access and change the database.
* Model Deployment with Cloud Run: We use a service called Cloud Run to run our machine learning models.
* Automated Model Updates with Cloud Functions and Scheduler: We use Cloud Functions and Scheduler, which are tools provided by Google Cloud, to automatically update the model at specific times.
* Google Cloud Storage for Model and Code Storage: We use Google Cloud Storage to store our machine learning models and the code used to deploy them.

## Deployment
N/A
