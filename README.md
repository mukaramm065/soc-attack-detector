# SOC Attack Detector

This project is a simple Java program that analyzes authentication logs and detects suspicious login activity such as brute-force attacks.

The program reads login logs, counts repeated failed login attempts, and detects successful logins that occur after multiple failures.

This project was built alongside my SOC lab environment to better understand how authentication attacks can be detected from logs.

## Example log format

2026-03-06 00:15:10 FAILED_LOGIN user=admin ip=192.168.1.20

## Features

- Parses login logs
- Detects repeated failed login attempts
- Flags possible brute-force attacks
- Generates a simple investigation summary

## Future Improvements

- detect password spraying
- support additional log formats
- analyze login activity across multiple hosts
