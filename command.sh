#!/bin/bash
git add . &&
git commit -m "$(date "+%Y-%m-%d %H:%M:%S")" &&
git pull origin $(git branch | awk -F* '{print $2}')
git push origin $(git branch | awk -F* '{print $2}')

