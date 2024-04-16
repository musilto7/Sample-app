#!/bin/bash

# Initialize an empty string for tags
tags=""

# Loop over all command-line arguments
for tag in "$@"
do
    # If tags is not empty, append a comma
    if [ -n "$tags" ]; then
        tags+=","
    fi

    # Append the current tag to the tags string
    tags+="$tag"
done

curl -X GET "https://api.flickr.com/services/feeds/photos_public.gne?format=json&nojsoncallback=1&tags=$tags"